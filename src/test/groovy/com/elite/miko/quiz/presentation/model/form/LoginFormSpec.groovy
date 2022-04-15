package com.elite.miko.quiz.presentation.model.form

import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Validation

class LoginFormSpec extends Specification {

    LoginForm target

    def validator = Validation.buildDefaultValidatorFactory().getValidator()

    def setup() {
        target = new LoginForm()
        target.setAccountId("accountId")
        target.setPassword("password")
    }

    def "正常系_accountId_12文字の文字列"() {
        given:
        target.accountId = "c" * 12

        when:
        def actual = validator.validate(target)

        then:
        actual == [] as Set
    }

    @Unroll
    def "異常系_accountId_#testName"() {
        given:
        target.accountId = input

        when:
        def actual = validator.validate(target)

        then:
        actual.size() == 1
        actual[0].message == expected

        where:
        testName | input    || expected
        "null"   | null     || "必須パラメータです"
        "空文字"    | ""       || "必須パラメータです"
        "13文字"   | "c" * 13 || "最大12文字で設定してください"
    }

    @Unroll
    def "passwordが未設定_#testName"() {
        given:
        target.password = input

        when:
        def actual = validator.validate(target)

        then:
        actual.size() == 1
        actual[0].message == expected

        where:
        testName | input || expected
        "null"   | null  || "必須パラメータです"
        "空文字"    | ""    || "必須パラメータです"
    }
}
