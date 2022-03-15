package com.joanne.expenseservice.entity

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Long,
    val name:String,
    val account:String,
    val password:String,
    val roleId:Long
)