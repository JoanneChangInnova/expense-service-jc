package com.joanne.expenseservice.service

import com.google.gson.Gson
import com.joanne.expenseservice.entity.Expense
import com.joanne.expenseservice.repository.ExpenseRepository
import com.joanne.expenseservice.vo.ExamineExpenseVo
import com.joanne.expenseservice.vo.QueueMsg
import com.joanne.expenseservice.vo.ResponseVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jms.core.JmsMessagingTemplate
import org.springframework.stereotype.Service

@Service
class ExamineService(private val expenseRepository: ExpenseRepository) {

    private val gson: Gson = Gson()

    @Autowired
    private val jmsMessagingTemplate= JmsMessagingTemplate()

    fun examine(expenseId: Long, examineExpenseVo: ExamineExpenseVo): ResponseVo<Expense> {
        var expense = expenseRepository.findById(expenseId)
        if (!expense.isPresent) return ResponseVo(400, "expenseId:${expenseId} doesn't found", null)
        if (examineExpenseVo.status.name.equals("Rejected") && examineExpenseVo.adminReason?.isBlank() == true) return ResponseVo(
            400,
            "if status is Rejected, adminReason must have value",
            null
        )
        //todo: check is valid ExpenseStatus
        var validExpense = expense.get()
        validExpense.status = examineExpenseVo.status.code
        validExpense.admin_reason = examineExpenseVo.adminReason
        validExpense.applyInfo = gson.toJson(examineExpenseVo)
        expenseRepository.save(validExpense)

        var qMsg= QueueMsg("Expense ID: $expenseId, has been updated")
        jmsMessagingTemplate.convertAndSend("applier-queue", gson.toJson(qMsg))
        return ResponseVo(200, "update Expense succesfully", validExpense)
    }
}