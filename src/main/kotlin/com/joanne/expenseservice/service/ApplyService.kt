package com.joanne.expenseservice.service

import com.joanne.expenseservice.entity.Expense
import com.joanne.expenseservice.repository.ExpenseRepository
import com.joanne.expenseservice.repository.UsersRepository
import com.joanne.expenseservice.vo.ApplyDataQueryCondition
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import au.com.console.jpaspecificationdsl.*
import com.google.gson.Gson
import com.joanne.expenseservice.enum.ExpenseStatus
import com.joanne.expenseservice.utils.DateUtils.dateStrToIsoDate
import com.joanne.expenseservice.vo.ApplyData
import com.joanne.expenseservice.vo.ExpensePageVo
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import kotlin.collections.ArrayList
import org.springframework.jms.core.JmsMessagingTemplate


@Service
class ApplyService
    (
    val usersRepository: UsersRepository,
    val expenseRepository: ExpenseRepository
) {

    private val gson: Gson = Gson()

    @Autowired
    private val jmsMessagingTemplate = JmsMessagingTemplate()

    fun getAllApplyList(condition: ApplyDataQueryCondition, page: Int, pageSize: Int): ExpensePageVo {
        val pageRequest = PageRequest.of(page, pageSize, Sort.by("id"))
        val pageRequest2 = PageRequest.of(page, pageSize)  //todo: see diff
        val user = usersRepository.getById(condition.userId)
        condition.roleId = user.roleId
        val result = expenseRepository.findAll(generateSpecification(condition), pageRequest)
        return ExpensePageVo(result.totalElements, result.totalPages, result.content)
    }

    fun generateSpecification(condition: ApplyDataQueryCondition): Specification<Expense>? = condition?.let {
        var specList: ArrayList<Specification<Expense>> = arrayListOf()
        if (condition != null && condition.roleId == 2L) {
            specList.add(Expense::userId.equal(it.userId))
        }
        if (it.type != null) {
            specList.add(Expense::type.equal(it.type))
        }
        if (it.status != null) {
            specList.add(Expense::status.equal(it.status))
        }
        if (it.startTime != null && it.endTime != null) {
            specList.add(
                Expense::createTime.between(
                    dateStrToIsoDate(it.startTime),
                    dateStrToIsoDate(it.endTime)
                )
            )
        }
        return and(specList)
    }

    fun createExpense(applyData: ApplyData){
        var expense= Expense(
            applyData.userId,
            applyData.type,
            ExpenseStatus.Submitted.code,
            applyData.amount,
            applyData.reason,
            Date(),
            applyData.startTime,
            applyData.endTime,
            applyData?.adminReason
        )
        expense.applyInfo=gson.toJson(expense)
        println("expense gson= "+gson.toJson(expense))
        expenseRepository.save(expense)
        sendMsg(expense.userId)
    }

    fun sendMsg(userId:Long){
        //todo: trace process
        jmsMessagingTemplate.convertAndSend(
            "approver-queue",
            "userID: ${userId}, has requested an expense application"
        )
    }

}