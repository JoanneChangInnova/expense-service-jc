package com.joanne.expenseservice.component


import com.joanne.expenseservice.processor.MsgProcessor
import com.joanne.expenseservice.vo.QueueMsg
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.dataformat.JsonLibrary
import org.springframework.stereotype.Component


@Component
class CamelRouter: RouteBuilder() {
    override fun configure() {
        from("activemq:queue:applier-queue")
            .unmarshal().json(JsonLibrary.Jackson, QueueMsg::class.java)
            .bean(MsgProcessor::class.java, "doProcess")
    }
}
