package com.joanne.expenseservice.controller

import com.joanne.expenseservice.service.ApplyService
import com.joanne.expenseservice.vo.ExpenseVo
import com.joanne.expenseservice.vo.ResponseVo
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping
@RestController
@CrossOrigin
@Api(value = "Apply", tags = ["Expense Service"])
class ApplyController(val applyservice: ApplyService) {

    fun getAllApplyList(
        //RequestBody
        ) : ResponseVo<ExpenseVo>{
        return ResponseVo(400, "Failed to get expense list", null)
    }
}