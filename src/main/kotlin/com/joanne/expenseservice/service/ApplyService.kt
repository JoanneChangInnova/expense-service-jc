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
import com.joanne.expenseservice.annotation.Slf4j.Companion.log
import com.joanne.expenseservice.enum.ExpenseStatus
import com.joanne.expenseservice.utils.DateUtils.dateStrToIsoDate
import com.joanne.expenseservice.vo.ApplyData
import com.joanne.expenseservice.vo.ExpensePageVo
import com.joanne.expenseservice.vo.ResponseVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import java.util.*
import kotlin.collections.ArrayList
import org.springframework.jms.core.JmsMessagingTemplate
import org.springframework.web.server.ResponseStatusException
import kotlin.NoSuchElementException


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
        val pageRequest2 = PageRequest.of(page, pageSize)
        val user = usersRepository.getById(condition.userId)
        condition.roleId = user.roleId
        val result = expenseRepository.findAll(generateSpecification(condition), pageRequest)
        return ExpensePageVo(result.totalElements, result.totalPages, result.content)
        //pageSize=10, 共回傳totalElements=30, totalPages=3, 使用Sort.by("id") 會照id先後回傳前30筆
        //若沒有用Sort.by("id")就會隨機顯示30筆
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

    fun createExpense(applyData: ApplyData): Boolean {
        try {
            var expense = Expense(
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
            expense.applyInfo = gson.toJson(expense)
            println("expense gson= " + gson.toJson(expense))
            expenseRepository.save(expense)
            return true
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST,"Failed to apply expense, ${gson.toJson(applyData)}, reason :$e")
        }
    }

    fun sendMsg(userId: Long) {
        try {
            jmsMessagingTemplate.convertAndSend(
                "approver-queue",
                "userID: ${userId}, has requested an expense application"
            )
            log.info("message to approver-queue had been sent")
        }catch(e: Exception){
            throw Exception("Send message to ActiveMQ failed, $e")
        }
    }

    fun getExpenseById(id: Long) = expenseRepository.findById(id)

}