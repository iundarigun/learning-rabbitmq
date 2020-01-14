package br.com.devcave.rabbit

import br.com.devcave.rabbit.consumer.ConsumerClient
import br.com.devcave.rabbit.producer.Producer2Client
import br.com.devcave.rabbit.producer.Producer1Client
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding

@SpringBootApplication
@EnableBinding(Producer1Client::class,Producer2Client::class, ConsumerClient::class)
class RabbitCloudStreamApplication

fun main(args: Array<String>) {
	runApplication<RabbitCloudStreamApplication>(*args)
}
