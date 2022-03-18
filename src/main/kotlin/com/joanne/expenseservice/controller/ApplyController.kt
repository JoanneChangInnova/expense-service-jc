package com.joanne.expenseservice.controller

import com.google.gson.Gson
import com.joanne.expenseservice.annotation.Slf4j
import com.joanne.expenseservice.annotation.Slf4j.Companion.log
import com.joanne.expenseservice.enum.ExpenseEnume
import com.joanne.expenseservice.service.ApplyService
import com.joanne.expenseservice.vo.ApplyData
import com.joanne.expenseservice.vo.ApplyDataQueryCondition
import com.joanne.expenseservice.vo.ExpenseVo
import com.joanne.expenseservice.vo.ResponseVo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RequestMapping
@RestController
@CrossOrigin
@Slf4j
class ApplyController(val applyservice: ApplyService,private val gson: Gson = Gson()) {

    //todo: wait for showing user inserted expenses
    @GetMapping("/apply")
    fun getApplyList(
        /* request query string */
        @RequestParam(required = false) userId: Long,
        @RequestParam(required = false) type: Int?,
        @RequestParam(required = false) status: Int?,
        @RequestParam(required = false) startTime: String?,
        @RequestParam(required = false) endTime: String?,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") pageSize: Int)
            : ResponseVo<ExpenseVo> {
        println("request userId!!!!!!!!!!!!!!!!!!:$userId")
        println("request page!!!!!!!!!!!!!!!!!!:$page")
        return try {
            val condition = ApplyDataQueryCondition(userId, null, type, status, startTime, endTime)
            val expenseVo = applyservice.getAllApplyList(condition, page, pageSize)
            ResponseVo(200, "succeeded", expenseVo)
        } catch (e: Exception) {
            ResponseVo(400, "failed"+e.stackTrace, null)
        }
    }


    @PostMapping("/apply")
    fun getAllApplyList(applyData: ApplyData): ResponseVo<ExpenseVo> {
        //這個log印不出東西？？是在搞笑？
//        log.info("print request body:",applyDataQueryCondition.toString())
//        log.info("print request body userId:",applyDataQueryCondition.userId)
        println("print request body:$applyData")

        try {
            // if the status contains traveling or group meal, column amount should be validated
            if (ExpenseEnume.Traveling.code.equals(applyData.type) && applyData.amount > 5000) {
                return ResponseVo(400, "Failed to apply the traveling expense", null)
            }
            if (ExpenseEnume.GroupMeal.code.equals(applyData.type) && applyData.amount > 2000) {
                return ResponseVo(400, "Failed to apply the group meal expense", null)
            }
            applyservice.createExpense(applyData)
            return ResponseVo(200, "Apply successfully", null)
        } catch (e: Exception) {
            println("Failed to apply expense: ${gson.toJson(applyData)}") // todo:see diff from println
            return ResponseVo(400, "Failed to apply expense", null)
        }
    }
}