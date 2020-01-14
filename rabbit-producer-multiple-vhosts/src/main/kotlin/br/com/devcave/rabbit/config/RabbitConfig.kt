package br.com.devcave.rabbit.config

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary


@Configuration
@EnableConfigurationProperties(ConnectionConfig::class)
class RabbitConfig(private val connectionConfig: ConnectionConfig) {

    @Bean
    @Primary
    fun primaryConnectionFactory():ConnectionFactory {
        return CachingConnectionFactory().apply {
            this.host = connectionConfig.host
            this.port = connectionConfig.port
            this.username = connectionConfig.username
            this.setPassword(connectionConfig.password)
            this.virtualHost = connectionConfig.virtualHost
        }
    }

    @Bean
    fun alternativeConnectionFactory():ConnectionFactory {
        return CachingConnectionFactory().apply {
            this.host = connectionConfig.alternativeHost
            this.port = connectionConfig.alternativePort
            this.username = connectionConfig.alternativeUsername
            this.setPassword(connectionConfig.alternativePassword)
            this.virtualHost = connectionConfig.alternativeVirtualHost
        }
    }

    @Bean
    fun primaryRabbitTemplate(): RabbitTemplate {
        return RabbitTemplate(primaryConnectionFactory())
    }

    @Bean
    fun alternativeRabbitTemplate(): RabbitTemplate {
        return RabbitTemplate(alternativeConnectionFactory())
    }
}