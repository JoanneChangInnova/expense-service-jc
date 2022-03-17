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
    /* request query string */
    fun getApplyList(@RequestParam(required = false) userId: Long,
                     @RequestParam(required = false) type: Int?,
                     @RequestParam(required = false) status: Int?,
                     @RequestParam(required = false) startTime: String?,
                     @RequestParam(required = false) endTime: String?,
                     @RequestParam(required = false, defaultValue = "0")page: Int,
                     @RequestParam(required = false, defaultValue = "10")pageSize: Int)
    {
        println("request userId:$userId")
        val condition = ApplyDataQueryCondition(userId,null,type,status,startTime,endTime)
        applyservice.getAllApplyList(condition,page,pageSize)
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