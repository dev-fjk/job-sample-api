package com.elite.miko.quiz.presentation.controller

import com.elite.miko.quiz.application.common.config.TokenConfig
import com.elite.miko.quiz.application.common.utility.WebTokenUtil
import spock.lang.Specification
import spock.lang.Unroll

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.nio.file.AccessDeniedException

class QuizAuthorizationInterceptorSpec extends Specification {

    QuizAuthorizationInterceptor target

    def tokenConfig = Mock(TokenConfig)
    def webTokenUtil = Mock(WebTokenUtil)

    def setup() {
        target = new QuizAuthorizationInterceptor(tokenConfig, webTokenUtil)
    }

    def "正常系_認証不要なURI"() {
        given:
        def request = Mock(HttpServletRequest)
        request.getRequestURI() >> "miko/v1/client"

        def handler = new Object()

        when:
        def actual = target.preHandle(request, Mock(HttpServletResponse), handler)

        then:
        actual
        0 * webTokenUtil.analysisFromToken(*_)
        0 * tokenConfig.getSubject()
    }

    def "正常系_認証に成功"() {
        given:
        def request = Mock(HttpServletRequest)
        request.getRequestURI() >> "miko/v1/admin/quizzes"
        request.getHeader("Authorization") >> "Bearer JsonWebToken"

        def handler = new Object()

        when:
        def actual = target.preHandle(request, Mock(HttpServletResponse), handler)

        then:
        actual
        1 * webTokenUtil.analysisFromToken(*_) >> "subject"
        1 * tokenConfig.getSubject() >> "subject"
    }

    @Unroll
    def "異常系_認証ヘッダーが異常値_#testName"() {
        given:
        def request = Mock(HttpServletRequest)
        request.getRequestURI() >> "miko/v1/admin/quizzes"
        request.getHeader("Authorization") >> header

        def handler = new Object()

        0 * webTokenUtil.analysisFromToken(*_)
        0 * tokenConfig.getSubject()

        when:
        target.preHandle(request, Mock(HttpServletResponse), handler)

        then:
        def exception = thrown(AccessDeniedException)
        exception.getMessage() == "認証に失敗しました"

        where:
        testName         | header
        "null"           | null
        "空文字"            | ""
        "Bearerが含まれていない" | "dummy"
    }

    def "異常系_認証に失敗"() {
        given:
        def request = Mock(HttpServletRequest)
        request.getRequestURI() >> "miko/v1/admin/quizzes"
        request.getHeader("Authorization") >> "Bearer JsonWebToken"

        def handler = new Object()

        when:
        target.preHandle(request, Mock(HttpServletResponse), handler)

        then:
        1 * webTokenUtil.analysisFromToken(*_) >> "subject"
        1 * tokenConfig.getSubject() >> "dummy"

        def exception = thrown(AccessDeniedException)
        exception.getMessage() == "認証に失敗しました"
    }

    def "異常系_トークン解析時に例外が発生"() {
        given:
        def request = Mock(HttpServletRequest)
        request.getRequestURI() >> "miko/v1/admin/quizzes"
        request.getHeader("Authorization") >> "Bearer JsonWebToken"

        def handler = new Object()

        1 * webTokenUtil.analysisFromToken(*_) >> {
            throw new RuntimeException()
        }
        0 * tokenConfig.getSubject()

        when:
        target.preHandle(request, Mock(HttpServletResponse), handler)

        then:
        def exception = thrown(AccessDeniedException)
        exception.getMessage() == "認証に失敗しました"
    }
}
