package com.joanne.expenseservice.entity

import com.joanne.expenseservice.annotation.NoArg
import java.util.*
import javax.persistence.*

@Entity
@NoArg
@Table(name = "expense")
data class Expense(
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
){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Long?=0
}
