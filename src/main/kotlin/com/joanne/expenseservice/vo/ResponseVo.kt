package com.joanne.expenseservice.vo

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseVo<T>(
    var code: HttpStatus =HttpStatus.BAD_REQUEST,
    var msg: String="Init failed",
    var data: T?
)
