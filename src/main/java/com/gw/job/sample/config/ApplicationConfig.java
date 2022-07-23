package com.gw.job.sample.config;

import java.time.Clock;
import java.time.ZoneId;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * アプリケーション共通のDI定義クラス
 */
@Configuration
public class ApplicationConfig {

    // 日本向けTimeZoneの設定
    private static final String JP_TIME_ZONE = "Asia/Tokyo";

    /**
     * 日本標準時のタイムゾーンを持つClockを生成
     *
     * @return Clock
     */
    @Bean
    public Clock clock() {
        return Clock.system(ZoneId.of(JP_TIME_ZONE));
    }

    /**
     * ModelMapper
     *
     * @return ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
