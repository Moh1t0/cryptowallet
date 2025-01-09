package com.javaacademy.cryptowallet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.service")
public class AppConfigUsd {
    private String url;
    private String header;
    private String token;
}
