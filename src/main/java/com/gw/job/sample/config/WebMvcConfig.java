package com.gw.job.sample.config;

import com.gw.job.sample.presentation.controller.QuizAuthorizationInterceptor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("!noauth")
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final QuizAuthorizationInterceptor quizAuthorizationInterceptor;

    /**
     * Interceptorの追加を行う
     *
     * @param registry : レジストリ
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(quizAuthorizationInterceptor);
    }
}
