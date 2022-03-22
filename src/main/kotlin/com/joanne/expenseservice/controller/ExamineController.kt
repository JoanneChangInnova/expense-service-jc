package com.joanne.expenseservice.controller

import com.joanne.expenseservice.entity.Expense
import com.joanne.expenseservice.service.ExamineService
import com.joanne.expenseservice.vo.ExamineExpenseVo
import com.joanne.expenseservice.vo.ResponseVo
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
class ExamineController(val examineService: ExamineService) {

    @PatchMapping("/apply/{id}")
    fun updateExpense(
        @PathVariable("id") expenseId: Long,
        @RequestBody examineExpense: ExamineExpenseVo
    ): ResponseVo<Expense> {
        return examineService.examine(expenseId,examineExpense)
    }
}