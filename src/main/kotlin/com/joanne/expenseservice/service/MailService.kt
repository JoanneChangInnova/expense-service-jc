package com.joanne.expenseservice.service

import com.joanne.expenseservice.vo.MailObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailService(@Autowired var javaMailSender: JavaMailSender) {

    fun mailSender(mailObject: MailObject){
        var message= SimpleMailMessage()
        message.setTo("joanne.chang@innovasolutions.com")
        message.setSubject(mailObject.subject)
        message.setText(mailObject.text)
        javaMailSender.send(message)
    }
}