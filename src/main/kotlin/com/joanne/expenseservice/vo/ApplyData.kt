package com.joanne.expenseservice.vo

import java.util.*

class ApplyData(
    var userId: Long,
    var type: Int,
    var amount: Int,
    var reason: String,
    var startTime: Date,
    var endTime: Date,
    var adminReason: String?
)