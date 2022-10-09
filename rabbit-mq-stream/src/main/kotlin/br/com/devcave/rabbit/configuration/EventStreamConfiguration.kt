package br.com.devcave.rabbit.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.stream.Environment
import com.rabbitmq.stream.OffsetSpecification
import com.rabbitmq.stream.impl.StreamEnvironmentBuilder
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.amqp.RabbitProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.rabbit.stream.config.StreamRabbitListenerContainerFactory
import org.springframework.rabbit.stream.listener.StreamListenerContainer
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate
import org.springframework.rabbit.stream.retry.StreamRetryOperationsInterceptorFactoryBean
import org.springframework.retry.support.RetryTemplate
import org.springframework.util.unit.DataSize
import java.time.Duration
import java.util.concurrent.TimeUnit


@Configuration
class EventStreamConfiguration(
        @Value("\${spring.application.name}")
        private val applicationName: String
) {

    fun env(rabbitProperties: RabbitProperties): Environment =
        rabbitProperties.toEnvironment()

    @Bean
    fun jackson2JsonMessageConverter(objectMapper: ObjectMapper): Jackson2JsonMessageConverter =
            Jackson2JsonMessageConverter(objectMapper)

    @Bean
    fun rabbitAdmin(connectionFactory: ConnectionFactory): RabbitAdmin =
            RabbitAdmin(connectionFactory)

    @Bean
    fun rabbitStreamTemplate(
            rabbitProperties: RabbitProperties,
            exchange: Exchange,
            jackson2JsonMessageConverter: Jackson2JsonMessageConverter): RabbitStreamTemplate {
        return RabbitStreamTemplate(rabbitProperties.toEnvironment(), exchange.name).also { template ->
            template.setMessageConverter(jackson2JsonMessageConverter)
            template.setProducerCustomizer { _, builder ->
                builder
                        .routing { it.toString() }
                        .producerBuilder()
            }
        }
    }

    @Bean
    fun streamContainerFactory(rabbitProperties: RabbitProperties): RabbitListenerContainerFactory<StreamListenerContainer> {
        return StreamRabbitListenerContainerFactory(rabbitProperties.toEnvironment()).also { factory ->
            factory.setNativeListener(true)
            factory.setConsumerCustomizer { _, builder ->
                builder.name(applicationName)
                        .offset(OffsetSpecification.first())
                        // .manualTrackingStrategy()
                        .autoTrackingStrategy()
                        .flushInterval(Duration.ofSeconds(20))
            }
        }
    }

    @Bean
    fun streamRetryOperationsInterceptorFactoryBean(): StreamRetryOperationsInterceptorFactoryBean {
        return StreamRetryOperationsInterceptorFactoryBean().also { interceptor ->
            interceptor.setRetryOperations(RetryTemplate.builder()
                    .infiniteRetry()
                    .exponentialBackoff(
                            TimeUnit.SECONDS.toMillis(10),
                            1.5,
                            TimeUnit.MINUTES.toMillis(5))
                    .build())
        }
    }

    @Bean
    fun exchange(): Exchange =
            ExchangeBuilder
                    .directExchange(applicationName)
                    .build()

    @Bean
    fun streamPartition(): Queue =
            QueueBuilder
                    .durable("partition-1")
                    .stream()
                    .withArgument("x-max-age", "7D")
                    .withArgument("x-stream-max-segment-size-bytes", DataSize
                            .ofMegabytes(10).toBytes())
                    .build()

    @Bean
    fun streamPartitionBind(rabbitAdmin: RabbitAdmin, exchange: Exchange, streamPartition: Queue): Binding =
            BindingBuilder
                    .bind(streamPartition)
                    .to(exchange)
                    .with("")
                    .noargs().also {
                        rabbitAdmin.declareQueue(streamPartition)
                        rabbitAdmin.declareExchange(exchange)
                        rabbitAdmin.declareBinding(it)
                    }
}

private fun RabbitProperties.toEnvironment(): Environment =
        StreamEnvironmentBuilder()
                .host(this.host)
                .port(this.stream.port)
                .username(this.username)
                .password(this.password)
                .virtualHost(this.virtualHost)
                .build()


