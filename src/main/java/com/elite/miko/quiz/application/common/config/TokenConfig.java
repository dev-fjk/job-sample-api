package com.elite.miko.quiz.application.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 認証トークンのキー情報を持つConfig
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "extension.token")
public class TokenConfig {
    private String subject;
    private String tokenKey;
}
