package com.joanne.expenseservice.controller

import com.google.gson.Gson
import com.joanne.expenseservice.annotation.Slf4j
import com.joanne.expenseservice.enum.ExpenseStatus
import com.joanne.expenseservice.enum.ExpenseType

import com.joanne.expenseservice.service.ApplyService
import com.joanne.expenseservice.vo.ApplyData
import com.joanne.expenseservice.vo.ApplyDataQueryCondition
import com.joanne.expenseservice.vo.ExpensePageVo
import com.joanne.expenseservice.vo.ResponseVo
import org.springframework.web.bind.annotation.*

@RequestMapping
@RestController
@CrossOrigin
@Slf4j
class ApplyController(val applyservice: ApplyService,private val gson: Gson = Gson()) {

    //todo: wait for showing user inserted expenses
    @GetMapping("/apply")
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
            ResponseVo(200, "succeeded", expenseVo)
        } catch (e: Exception) {
            ResponseVo(400, "failed"+e.stackTrace, null)
        }
    }


    @PostMapping("/apply")
    fun applyExpense(@RequestBody applyData: ApplyData): ResponseVo<ExpensePageVo> {
        //這個log印不出東西？？是在搞笑？
//        log.info("print request body:",applyDataQueryCondition.toString())
//        log.info("print request body userId:",applyDataQueryCondition.userId)
        println("print request body:$applyData")

        try {
            if (ExpenseType.Traveling.code.equals(applyData.type) && applyData.amount > 5000) {
                return ResponseVo(400, "Failed to apply the traveling expense", null)
            }
            if (ExpenseType.GroupMeal.code.equals(applyData.type) && applyData.amount > 2000) {
                return ResponseVo(400, "Failed to apply the group meal expense", null)
            }
            applyservice.createExpense(applyData)
            return ResponseVo(200, "Apply successfully", null)
        } catch (e: Exception) {
            //todo: how to split catching sendMsg from apply?
            println("Failed to apply expense: ${gson.toJson(applyData)} ,reason:$e") // without toJson, only address will be printed
            return ResponseVo(400, "Failed to apply expense", null)
        }

    }
}