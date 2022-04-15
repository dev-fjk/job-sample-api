package com.gw.job.sample;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@OpenAPIDefinition(info = @Info(
        title = "求人 サンプルAPI",
        description = "求人PJ 事前学習用のAPIです",
        version = "v1")
)
public class JobApplication {

    /**
     * root
     *
     * @param args : プログラム引数
     */
    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }
}
