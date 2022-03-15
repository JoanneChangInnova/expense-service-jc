package com.joanne.expenseservice.vo

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseVo<T>(
    var code: Int=444,
    var msg: String="Init failed",
    var data: T?
)
