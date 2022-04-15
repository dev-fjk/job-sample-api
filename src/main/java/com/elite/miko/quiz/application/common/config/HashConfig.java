package com.elite.miko.quiz.application.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * パスワードのハッシュ化に使用する文字列定義クラス
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "extension.hash")
public class HashConfig {
    private String salt;
}
