package br.com.devcave.rabbit.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller
import springfox.documentation.swagger2.annotations.EnableSwagger2
import org.springframework.web.bind.annotation.GetMapping
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import javax.servlet.ServletContext
import org.springframework.context.annotation.Bean

@Controller
@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun swaggerSettings(servletContext: ServletContext): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("br.com.devcave.rabbit.controller"))
            .paths(PathSelectors.any())
            .build()
    }

    @GetMapping("/")
    fun index(): String {
        return "redirect:swagger-ui.html"
    }
}