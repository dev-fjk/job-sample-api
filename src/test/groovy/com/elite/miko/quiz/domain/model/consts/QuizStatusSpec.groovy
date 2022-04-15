package com.elite.miko.quiz.domain.model.consts


import spock.lang.Specification

class QuizStatusSpec extends Specification {

    def "正常系_create_パターン網羅_#testName"() {
        when:
        def actual = QuizStatus.create(input)
        then:
        actual == expected
        where:
        testName   | input || expected
        "有効"       | "1"   || QuizStatus.ENABLED
        "追加リクエスト中" | "2"   || QuizStatus.REQUEST
        "無効"       | "9"   || QuizStatus.DISABLED
    }

    def "異常系_create_パターン網羅_#testName"() {
        when:
        def actual = QuizStatus.create(input)
        then:
        actual == expected
        where:
        testName | input     || expected
        "異常値"    | "invalid" || QuizStatus.INVALID
        "null"   | null      || null
    }

    def "異常系_create_定義されていない値"() {
        when:
        QuizStatus.create("9999")
        then:
        def exception = thrown(IllegalArgumentException)
        exception.getMessage() == "quizStatusが異常な値です"
    }

    def "正常系_of_パターン網羅_#testName"() {
        when:
        def actual = QuizStatus.of(input)
        then:
        actual == expected
        actual.getDisplayName() == displayName
        where:
        testName   | input || expected            || displayName
        "有効"       | "1"   || QuizStatus.ENABLED  || "有効"
        "追加リクエスト中" | "2"   || QuizStatus.REQUEST  || "追加リクエスト中"
        "無効"       | "9"   || QuizStatus.DISABLED || "無効"
    }

    def "異常系_of_invalidという文字列が設定"() {
        when:
        QuizStatus.of("invalid")
        then:
        def exception = thrown(IllegalArgumentException)
        exception.getMessage() == "quizStatusが異常な値です"

    }

    def "異常系_of_パターン網羅_#testName"() {
        when:
        QuizStatus.of(input)
        then:
        def exception = thrown(IllegalArgumentException)
        exception.getMessage() == message

        where:
        testName | input   || message
        "異常値"    | "99999" || "quizStatusが異常な値です"
        "null"   | null    || "quizStatusが設定されていません"
    }
}
