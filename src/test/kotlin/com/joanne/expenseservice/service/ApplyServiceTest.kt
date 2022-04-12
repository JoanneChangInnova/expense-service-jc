package com.joanne.expenseservice.service

import com.joanne.expenseservice.enum.ExpenseStatus
import com.joanne.expenseservice.vo.ApplyData
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class ApplyServiceTest(){

    @Autowired
    private lateinit var service: ApplyService

    @Test
    fun `createExpenseTest`() {
        var data= ApplyData(
            1,5, ExpenseStatus.Submitted.code,
            "test",Date(), Date(),"")
        var isCreated = service.createExpense(data)
        Assertions.assertEquals(true, isCreated);
    }
}
