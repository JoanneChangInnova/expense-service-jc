package com.joanne.expenseservice.entity

import com.joanne.expenseservice.annotation.NoArg
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@NoArg
class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long?,
    var name:String?,
    var account:String?,
    var password:String?,
    var roleId:Long?
)