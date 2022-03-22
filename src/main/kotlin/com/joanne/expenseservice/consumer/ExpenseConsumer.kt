package com.joanne.expenseservice.consumer

import com.joanne.expenseservice.service.MailService
import com.joanne.expenseservice.vo.MailObject
import org.springframework.jms.annotation.JmsListener
import org.springframework.web.bind.annotation.RestController

@RestController
class ExpenseConsumer(val mailService: MailService) {

    @JmsListener(destination = "approver-queue")
    fun testConsumer(msg: String){
        try {
            println("JMS Listener received msg from approver-queue, msg: $msg")
            var mailObject= MailObject("","msg from approver-queue","msg consumed by JMS listener: $msg")
            mailService.mailSender(mailObject)
        }catch (e:Exception){
            println("Failed: ${e.message}")
        }
    }
}