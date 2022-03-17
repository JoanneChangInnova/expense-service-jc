package com.joanne.expenseservice.repository

import com.joanne.expenseservice.entity.Expense
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExpenseRepository : JpaRepository<Expense,Long>{
}