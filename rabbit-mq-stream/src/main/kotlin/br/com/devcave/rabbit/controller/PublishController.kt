package br.com.devcave.rabbit.controller

import br.com.devcave.rabbit.domain.EmployeeRequest
import org.slf4j.LoggerFactory
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("publish")
class PublishController(
        private val rabbitStreamTemplate: RabbitStreamTemplate
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun publish(@RequestBody request:EmployeeRequest) {
        logger.info("Publisher new request: $request")
        rabbitStreamTemplate.convertAndSend(request)
    }
}