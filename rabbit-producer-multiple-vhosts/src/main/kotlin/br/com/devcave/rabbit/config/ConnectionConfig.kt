package br.com.devcave.rabbit.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.rabbitmq")
data class ConnectionConfig(
    val host:String,
    val port: Int,
    val username: String,
    val password: String,
    val virtualHost: String,
    val alternativeHost:String,
    val alternativePort: Int,
    val alternativeUsername: String,
    val alternativePassword: String,
    val alternativeVirtualHost: String
)