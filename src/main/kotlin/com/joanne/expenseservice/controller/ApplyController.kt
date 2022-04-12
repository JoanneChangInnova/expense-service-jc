package com.joanne.expenseservice.controller

import com.google.gson.Gson
import com.joanne.expenseservice.annotation.Slf4j
import com.joanne.expenseservice.annotation.Slf4j.Companion.log
import com.joanne.expenseservice.entity.Expense
import com.joanne.expenseservice.enum.ExpenseStatus
import com.joanne.expenseservice.enum.ExpenseType

import com.joanne.expenseservice.service.ApplyService
import com.joanne.expenseservice.vo.ApplyData
import com.joanne.expenseservice.vo.ApplyDataQueryCondition
import com.joanne.expenseservice.vo.ExpensePageVo
import com.joanne.expenseservice.vo.ResponseVo
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping
@RestController
@CrossOrigin
@Slf4j
@Api(value = "Apply", tags = ["Expense Service"])
class ApplyController(val applyservice: ApplyService,private val gson: Gson = Gson()) {

    @GetMapping("/apply")
    @ApiOperation("Get All Expense List")
    fun getAllApplyList(
        /* request query string */
        @RequestParam(required = false) userId: Long,
        @RequestParam(required = false) type: Int?,
        @RequestParam(required = false) status: Int?,
        @RequestParam(required = false) startTime: String?,
        @RequestParam(required = false) endTime: String?,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") pageSize: Int)
            : ResponseVo<ExpensePageVo> {
        return try {
            val condition = ApplyDataQueryCondition(userId, null, type, status, startTime, endTime)
            val expenseVo = applyservice.getAllApplyList(condition, page, pageSize)
            ResponseVo(HttpStatus.ACCEPTED, "succeeded", expenseVo)
        } catch (e: Exception) {
            ResponseVo(HttpStatus.BAD_REQUEST, "failed"+e.stackTrace, null)
        }
    }


    @PostMapping("/apply")
    @ApiOperation("Apply new expense")
    fun applyExpense(@RequestBody applyData: ApplyData): ResponseVo<ExpensePageVo> {
        try {
            if (ExpenseType.Traveling.code.equals(applyData.type) && applyData.amount > 5000) {
                return ResponseVo(HttpStatus.BAD_REQUEST, "Failed to apply the traveling expense", null)
            }
            if (ExpenseType.GroupMeal.code.equals(applyData.type) && applyData.amount > 2000) {
                return ResponseVo(HttpStatus.BAD_REQUEST, "Failed to apply the group meal expense", null)
            }
            //if createExpense succeed, send msg to activemq
            if(applyservice.createExpense(applyData)) applyservice.sendMsg(applyData.userId)
            return ResponseVo(HttpStatus.CREATED, "Apply successfully", null)
        } catch (e: Exception) {
            log.debug("Failed to apply expense: ${gson.toJson(applyData)}") // without toJson, only address will be printed
            return ResponseVo(HttpStatus.BAD_REQUEST, "Failed , reason:$e", null)// could catch multiple exception from createExpense() and sendMsg()
        }

    }

    @GetMapping("/apply/{id}")
    @ApiOperation("Get Expense By Id")
    fun getExpenseInfoById(@PathVariable("id") expenseId: Long): ResponseVo<Expense> {
        log.info("print info!!!!!:$expenseId")
        log.warn("print warn:$expenseId")
        log.error("print error:$expenseId")
        println(log.isDebugEnabled) //false -> true
        log.debug("print debug:$expenseId")  //does get evaluated after application.yml enabled debug level

        try {
            var result = applyservice.getExpenseById(expenseId)
            return ResponseVo(HttpStatus.ACCEPTED, "get expense info successfully", result.get())
        } catch (e: Exception) {
            println("Failed to get expense info, id: $expenseId")
            return ResponseVo(HttpStatus.BAD_REQUEST, "Failed to get expense info", null)
        }
    }
}