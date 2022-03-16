package com.joanne.expenseservice.controller

import com.joanne.expenseservice.annotation.Slf4j
import com.joanne.expenseservice.annotation.Slf4j.Companion.log
import com.joanne.expenseservice.service.ApplyService
import com.joanne.expenseservice.vo.ApplyDataQueryCondition
import com.joanne.expenseservice.vo.ExpenseVo
import com.joanne.expenseservice.vo.ResponseVo
import org.springframework.web.bind.annotation.*

@RequestMapping
@RestController
@CrossOrigin
@Slf4j
class ApplyController(val applyservice: ApplyService) {

    @GetMapping("/apply")
    fun test(@RequestBody applyDataQueryCondition: ApplyDataQueryCondition){
        log.info("info test")
        System.out.println("getMapping request body:"+applyDataQueryCondition)

    }


    @PostMapping("/apply")
    fun getAllApplyList(@RequestBody applyDataQueryCondition: ApplyDataQueryCondition) : ResponseVo<ExpenseVo>{
        //這個log印不出東西？？是在搞笑？
//        log.info("print request body:",applyDataQueryCondition.toString())
//        log.info("print request body userId:",applyDataQueryCondition.userId)
        System.out.println("print request body:"+applyDataQueryCondition)
        return ResponseVo(400, "Failed to get expense list", null)
    }
}