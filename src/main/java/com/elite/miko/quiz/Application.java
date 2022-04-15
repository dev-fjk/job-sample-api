package com.elite.miko.quiz;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@OpenAPIDefinition(info = @Info(
        title = "さくらみこ クイズAPI",
        description = "さくらみこのクイズアプリのDB管理用のIFを提供します",
        version = "v1")
)
public class Application {

    /**
     * root
     *
     * @param args : プログラム引数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
