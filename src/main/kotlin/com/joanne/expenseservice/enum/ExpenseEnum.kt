package com.joanne.expenseservice.enum

enum class ExpenseEnume(val code: Int, val category: String) {
    //Status
    Submitted(1, "status"),
    Rejected(2, "status"),
    Approved(3, "status"),
    Canceled(4, "status"),
    //Type
    Traveling(5, "type"),
    GroupMeal(6, "type")
}