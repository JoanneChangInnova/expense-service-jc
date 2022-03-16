package com.joanne.expenseservice.entity

import com.joanne.expenseservice.annotation.NoArg
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@NoArg
@Table(name = "expense")
data class Expense(
    @Id
    val id:Long,
    val userId:Long,
    val type:Int,
    val status:Int,
    val amount:Long,
    val reason:String,
    val applyInfo:String,
    val createTime: Date,
    val startTime: Date,
    val endTime: Date,
    val admin_reason: String
)
