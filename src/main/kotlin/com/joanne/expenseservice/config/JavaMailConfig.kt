package com.joanne.expenseservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class JavaMailConfig {
//    @Bean
//    fun emailSender(): JavaMailSender? {
//        val mailSender = JavaMailSenderImpl()
//        mailSender.host = "smtp.gmail.com"
//        mailSender.port = 587
//        mailSender.username = "anchiao0417@gmail.com"
//        mailSender.password = "vgansukkzdfpxagi"
//        val props = mailSender.javaMailProperties
//        props.put("mail.transport.protocol", "smtp")
//        props.put("mail.smtp.auth", "true")
//        props.put("mail.smtp.starttls.enable", "true")
//        props.put("mail.debug", "true")
//        return mailSender
//    }
}