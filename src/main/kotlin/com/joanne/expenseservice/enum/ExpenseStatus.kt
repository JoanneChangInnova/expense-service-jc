package com.joanne.expenseservice.enum

enum class ExpenseStatus(val code: Int, val category: String) {
    Submitted(1, "status"),
    Rejected(2, "status"),
    Approved(3, "status"),
    Canceled(4, "status")
}