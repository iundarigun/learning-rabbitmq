package br.com.devcave.rabbit.controller

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("exchanges")
class ExchangeController(
    private val primaryRabbitTemplate: RabbitTemplate,
    private val alternativeRabbitTemplate: RabbitTemplate
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("primary/{exchange}/{routingKey}")
    fun postOnPrimaryExchange(
        @PathVariable exchange: String,
        @PathVariable routingKey: String,
        @RequestBody message: String
    ): HttpEntity<Any?> {
        log.info("sending message $message")
        primaryRabbitTemplate.convertAndSend(exchange, routingKey, message)
        return ResponseEntity.ok().build()
    }

    @PostMapping("alternative/{exchange}/{routingKey}")
    fun postOnAlternativeExchange(
        @PathVariable exchange: String,
        @PathVariable routingKey: String,
        @RequestBody message: String
    ): HttpEntity<Any?> {
        log.info("sending message $message")
        alternativeRabbitTemplate.convertAndSend(exchange, routingKey, message)
        return ResponseEntity.ok().build()
    }
}
