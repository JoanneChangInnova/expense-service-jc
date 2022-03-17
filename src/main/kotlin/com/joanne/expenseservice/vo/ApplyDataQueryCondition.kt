package com.joanne.expenseservice.vo

/* SQL Condition For Querying Apply List */
data class ApplyDataQueryCondition(
    val userId: Long,
    var roleId: Long?,
    val type: Int?,
    val status: Int?,
    val startTime: String?,
    val endTime: String?,
)
