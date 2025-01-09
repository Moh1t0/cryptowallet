package com.javaacademy.cryptowallet.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppSwagger {

    @Bean
    public OpenAPI customApi() {
        Info info = new Info().title("Это api Криптокошелька")
                .description("Данное приложение позволяет создать пользователя и криптосчета,"
                        + "а также пополнение и списание баланса");
        return new OpenAPI().info(info);
    }
}
