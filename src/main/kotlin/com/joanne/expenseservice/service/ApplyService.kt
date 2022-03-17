package com.joanne.expenseservice.service

import com.joanne.expenseservice.entity.Expense
import com.joanne.expenseservice.repository.ExpenseRepository
import com.joanne.expenseservice.repository.UsersRepository
import com.joanne.expenseservice.vo.ApplyDataQueryCondition
import org.apache.commons.lang3.time.DateUtils
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import au.com.console.jpaspecificationdsl.*

@Service
class ApplyService
    (
    val usersRepository: UsersRepository,
    val expenseRepository: ExpenseRepository
    )
{

    fun getAllApplyList(condition: ApplyDataQueryCondition, page: Int, pageSize: Int) {
        val pageRequest= PageRequest.of(page,pageSize, Sort.by("id"))
        val pageRequest2= PageRequest.of(page,pageSize) //see diff
        val user = usersRepository.getById(condition.userId)
        if(user != null){
            val roleId = user.roleId
            condition.roleId = roleId
            println("roleId= $roleId")
        }else throw Exception("User not found!")
        expenseRepository.findAll(condirion,pageRequest)


    }

    fun generateSpecification(condition: ApplyDataQueryCondition): Specification<Expense>? = condition?.let {
        var specList: ArrayList<Specification<Expense>> = arrayListOf()
        // to check if user's role is user, just find its own data
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
                    DateUtils.dateStrToIsoDate(it.startTime),
                    DateUtils.dateStrToIsoDate(it.endTime)
                )
            )
        }
        return and(specList)
    }
}