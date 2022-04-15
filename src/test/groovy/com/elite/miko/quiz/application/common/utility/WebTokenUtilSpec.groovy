package com.elite.miko.quiz.application.common.utility

import com.elite.miko.quiz.application.common.config.TokenConfig
import spock.lang.Specification

import java.time.Clock
import java.time.ZoneId

class WebTokenUtilSpec extends Specification {

    WebTokenUtil target
    def tokenConfig = Mock(TokenConfig)
    def clock = mockClock()

    def setup() {
        target = new WebTokenUtil(tokenConfig, clock)
    }

    def "正常系_トークン生成後に解析するとsubjectの値が取り出せるか"() {
        given:
        tokenConfig.getSubject() >> "subject"
        tokenConfig.getTokenKey() >> "op5hhr4ysc7ymbxi7mixp604qp9fu651szr4n24wkc9x3ng26xccd1f1xz8ltokenkey"

        when:
        def generatedToken = target.generateToken()
        def actual = target.analysisFromToken(generatedToken)

        then:
        // トークンの生成に使用したsubjectが取り出せるか
        actual == "subject"
    }

    static Clock mockClock() {
        return Clock.system(ZoneId.of("Asia/Tokyo"))
    }
}
