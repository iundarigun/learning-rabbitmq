package br.com.devcave.rabbit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RabbitMqAdvancedApplication

fun main(args: Array<String>) {
    runApplication<RabbitMqAdvancedApplication>(*args)
}
