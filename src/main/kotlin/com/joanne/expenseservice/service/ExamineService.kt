package com.joanne.expenseservice.service

import com.google.gson.Gson
import com.joanne.expenseservice.entity.Expense
import com.joanne.expenseservice.enum.ExpenseStatus
import com.joanne.expenseservice.repository.ExpenseRepository
import com.joanne.expenseservice.vo.ExamineExpenseVo
import com.joanne.expenseservice.vo.QueueMsg
import com.joanne.expenseservice.vo.ResponseVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.jms.core.JmsMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class ExamineService(private val expenseRepository: ExpenseRepository) {

    private val gson: Gson = Gson()

    @Autowired
    private val jmsMessagingTemplate = JmsMessagingTemplate()

    fun examine(expense: Expense, examineExpenseVo: ExamineExpenseVo): ResponseVo<Expense> {
        expense.status = examineExpenseVo.status.code
        expense.admin_reason = examineExpenseVo.adminReason
        expense.applyInfo = gson.toJson(examineExpenseVo)
        expenseRepository.save(expense)
        return ResponseVo(HttpStatus.OK, "update Expense succesfully", expense)
    }

    fun checkExpense(expenseId: Long, examineExpenseVo: ExamineExpenseVo): Expense {
        var expense = expenseRepository.findById(expenseId)
        if (!expense.isPresent) throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "expenseId:${expenseId} not found",
            null
        )
        if (examineExpenseVo.status.equals(ExpenseStatus.Rejected) && examineExpenseVo.adminReason?.isBlank() == true)
            throw ResponseStatusException(
                HttpStatus.PRECONDITION_REQUIRED,
                "if status is Rejected, adminReason must have value"
            )
        if (!examineExpenseVo.status.equals(ExpenseStatus.Approved) && !examineExpenseVo.status.equals(ExpenseStatus.Canceled) && !examineExpenseVo.status.equals(
                ExpenseStatus.Rejected))
        {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Examine status not valid"
            )
        }
        return expense.get()
    }


    fun sendMsg(expenseId: Long) {
        try {
            var qMsg = QueueMsg("Expense ID: $expenseId, has been updated")
            jmsMessagingTemplate.convertAndSend("applier-queue", gson.toJson(qMsg))
            //Must convert to json string : jmsMessagingTemplate.convertAndSend("applier-queue", "Expense ID: ${expenseId}, has been updated")
        } catch (e: Exception) {
            throw Exception("Send message to ActiveMQ failed, $e")
        }
    }
}