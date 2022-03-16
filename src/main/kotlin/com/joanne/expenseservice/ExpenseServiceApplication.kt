package com.joanne.expenseservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@PropertySource("classpath:./activemq.properties")
@SpringBootApplication
class ExpenseServiceApplication

fun main(args: Array<String>) {
	runApplication<ExpenseServiceApplication>(*args)
}
