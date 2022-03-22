package com.joanne.expenseservice.config

import org.apache.activemq.ActiveMQConnectionFactory
import org.apache.activemq.command.ActiveMQQueue
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.core.JmsTemplate
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl


@Configuration
@EnableJms
class ActiveMqConfig {
    @Value("\${activemq.user}")
    private var user= String()
    @Value("\${activemq.password}")
    private var pwd= String()
    @Value("\${activemq.queueName}")
    private var queueName= String()
//    @Value("\${topicName}")
//    private var topicName= String()
    @Value("\${activemq.broker-url}")
    private var brokerUrl= String()

    @Bean
    fun queue():ActiveMQQueue{
        return ActiveMQQueue(queueName)
    }

//    @Bean
//    fun topic(): ActiveMQTopic {
//        return ActiveMQTopic(topicName)
//    }

    @Bean
    fun connectionFactory():ActiveMQConnectionFactory{
        return ActiveMQConnectionFactory(user,pwd ,brokerUrl)
    }

    @Bean
    fun jmsTemplate():JmsTemplate{
        return JmsTemplate(connectionFactory())
    }

    @Bean
    fun javaMailSender(): JavaMailSender? {
        return JavaMailSenderImpl()
    }
}