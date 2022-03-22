package com.joanne.expenseservice.vo

import com.joanne.expenseservice.enum.ExpenseStatus

data class ExamineExpenseVo(
    val approverId: Long,
    val status: ExpenseStatus,
){
    // if status is Rejected, adminReason must have value
    var adminReason: String? = ""
}
