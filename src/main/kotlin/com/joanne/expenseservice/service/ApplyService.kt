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
import com.joanne.expenseservice.utils.DateUtils.dateStrToIsoDate
import com.joanne.expenseservice.vo.ApplyData
import com.joanne.expenseservice.vo.ExpenseVo


@Service
class ApplyService
    (
    val usersRepository: UsersRepository,
    val expenseRepository: ExpenseRepository
) {

    fun getAllApplyList(condition: ApplyDataQueryCondition, page: Int, pageSize: Int): ExpenseVo {
        val pageRequest = PageRequest.of(page, pageSize, Sort.by("id"))
        val pageRequest2 = PageRequest.of(page, pageSize)  //todo: see diff
        val user = usersRepository.getById(condition.userId)
        condition.roleId = user.roleId
        val result = expenseRepository.findAll(generateSpecification(condition), pageRequest)
        return ExpenseVo(result.totalElements, result.totalPages, result.content)
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
//        val expense= Expense(applyData.userId,applyData.type,)
//        expenseRepository.save()
    }
}