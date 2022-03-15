package com.joanne.expenseservice.vo

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.joanne.expenseservice.entity.Users
import kotlinx.serialization.Serializable
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ExpenseVo(
    var totalElements: Long?,
    var totalPages: Int?,
    var expenses: List<
            //Expense
            Users
            >?
)