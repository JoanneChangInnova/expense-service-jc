package com.joanne.expenseservice.controller

import com.joanne.expenseservice.annotation.Slf4j.Companion.log
import com.joanne.expenseservice.entity.Expense
import com.joanne.expenseservice.service.ExamineService
import com.joanne.expenseservice.vo.ExamineExpenseVo
import com.joanne.expenseservice.vo.ResponseVo
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@Api(value = "Update", tags = ["Expense Service"])
class ExamineController(val examineService: ExamineService) {

    @PatchMapping("/apply/{id}")
    fun updateExpense(
        @PathVariable("id") expenseId: Long,
        @RequestBody examineExpense: ExamineExpenseVo
    ): ResponseVo<Expense> {
        try {
            var expense = examineService.checkExpense(expenseId, examineExpense)
            val responseVo = examineService.examine(expense, examineExpense)
            if(responseVo.code.equals(HttpStatus.OK)) examineService.sendMsg(expenseId)
            return responseVo
        }catch (e: Exception){
            log.debug("examine expense: ${examineExpense}")
            return ResponseVo(HttpStatus.BAD_REQUEST, "Failed , reason:$e", null)
        }
    }


}