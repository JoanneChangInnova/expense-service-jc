package com.joanne.expenseservice.processor

import com.joanne.expenseservice.service.MailService
import com.joanne.expenseservice.vo.MailObject
import com.joanne.expenseservice.vo.QueueMsg
import org.apache.camel.spi.annotations.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jms.annotation.JmsListener

class MsgProcessor{
    @Autowired
    lateinit var mailService: MailService
    fun doProcess(qMsg: QueueMsg){
        println("Camel got msg from applier-queue: ${qMsg.msg}")
        var mailObject= MailObject("","msg from applier-queue", "msg consumed by camel: "+qMsg.msg)
        mailService.mailSender(mailObject)
    }
}
