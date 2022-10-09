package br.com.devcave.rabbit.consumer

import br.com.devcave.rabbit.domain.EmployeeRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.stream.Message
import com.rabbitmq.stream.MessageHandler
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component

@Component
class EventConsumer(
        private val objectMapper: ObjectMapper
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Retryable(interceptor = "streamRetryOperationsInterceptorFactoryBean")
    @RabbitListener(queues = ["#{streamPartition.name}"], containerFactory = "streamContainerFactory")
    fun onConsumer(message: Message, context: MessageHandler.Context) {
        logger.info("Stream partition message offset: ${context.offset()}")
        val request = objectMapper.readValue(message.bodyAsBinary, EmployeeRequest::class.java)
        logger.info("request $request")
    }
}