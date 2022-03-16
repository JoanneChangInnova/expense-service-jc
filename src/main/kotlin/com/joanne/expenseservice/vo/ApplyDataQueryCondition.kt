package com.joanne.expenseservice.vo

data class ApplyDataQueryCondition(
    val userId: Long?,
    val expenseType: Short?,   //frontend name it type
    val status: Short?,
    val startTime: String?,
    val endTime: String?,
    val page: Short?,
    val pageSize: Short?
)
