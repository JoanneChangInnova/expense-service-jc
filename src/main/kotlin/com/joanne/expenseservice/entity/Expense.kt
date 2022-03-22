package com.joanne.expenseservice.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.joanne.expenseservice.annotation.NoArg
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import com.vladmihalcea.hibernate.type.json.JsonStringType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import java.util.*
import javax.persistence.*

@Entity
@NoArg
@Table(name = "expense")
@JsonInclude(JsonInclude.Include.NON_NULL)
@TypeDefs(
    TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
)
data class Expense(
    val userId:Long,
    val type:Int,
    var status:Int,
    val amount:Int,
    val reason:String,
    val createTime: Date,
    val startTime: Date,
    val endTime: Date,
    var admin_reason: String?
){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Long?=0

    /*
    * Store all input values of new and updated application in josonB type
    * */
    @Type(type = "jsonb")  //column is of type jsonb but expression is of type character varying(solution)
    @Column(columnDefinition = "json")
    var applyInfo:String?=null
}
