package com.elite.miko.quiz.presentation.model.form

import com.elite.miko.quiz.UnitTestBase
import com.fasterxml.jackson.core.type.TypeReference
import spock.lang.Unroll

import javax.validation.Validation

class QuizAddFormSpec extends UnitTestBase {

    QuizAddForm target
    def validator = Validation.buildDefaultValidatorFactory().getValidator()

    def setup() {
        target = readJsonToObject("/form/", "quizAddForm",
                new TypeReference<QuizAddForm>() {})
    }

    def "正常系_バリデーションエラー無し"() {
        when:
        def actual = validator.validate(target)

        then:
        actual == [] as Set
    }

    def "正常系_questionが200文字"() {
        given:
        target.question = "q" * 200

        when:
        def actual = validator.validate(target)

        then:
        actual == [] as Set
    }

    @Unroll
    def "異常系_questionでバリデーションエラー_#testName"() {
        given:
        target.question = input

        when:
        def actual = validator.validate(target)

        then:
        actual.size() == 1
        actual[0].message == expected

        where:
        testName | input     || expected
        "null"   | null      || "必須パラメータです"
        "空文字"    | ""        || "必須パラメータです"
        "201文字"  | "q" * 201 || "最大200文字で設定してください"
    }

    def "正常系_commentaryが200文字"() {
        given:
        target.commentary = "c" * 200

        when:
        def actual = validator.validate(target)

        then:
        actual == [] as Set
    }

    @Unroll
    def "異常系_commentaryでバリデーションエラー_#testName"() {
        given:
        target.commentary = input

        when:
        def actual = validator.validate(target)

        then:
        actual.size() == 1
        actual[0].message == expected

        where:
        testName | input     || expected
        "null"   | null      || "必須パラメータです"
        "空文字"    | ""        || "必須パラメータです"
        "51文字"   | "c" * 201 || "最大200文字で設定してください"
    }

    def "正常系_answer1が50文字"() {
        given:
        target.answer.answer1 = "a" * 50

        when:
        def actual = validator.validate(target)

        then:
        actual == [] as Set
    }

    @Unroll
    def "異常系_answer1でバリデーションエラー_#testName"() {
        given:
        target.answer.answer1 = input

        when:
        def actual = validator.validate(target)

        then:
        actual.size() == 1
        actual[0].message == expected

        where:
        testName | input    || expected
        "null"   | null     || "必須パラメータです"
        "空文字"    | ""       || "必須パラメータです"
        "51文字"   | "c" * 51 || "最大50文字で設定してください"
    }

    def "正常系_answer2が50文字"() {
        given:
        target.answer.answer2 = "a" * 50

        when:
        def actual = validator.validate(target)

        then:
        actual == [] as Set
    }

    @Unroll
    def "異常系_answer2でバリデーションエラー_#testName"() {
        given:
        target.answer.answer2 = input

        when:
        def actual = validator.validate(target)

        then:
        actual.size() == 1
        actual[0].message == expected

        where:
        testName | input    || expected
        "null"   | null     || "必須パラメータです"
        "空文字"    | ""       || "必須パラメータです"
        "51文字"   | "c" * 51 || "最大50文字で設定してください"
    }

    def "正常系_answer3が50文字"() {
        given:
        target.answer.answer3 = "a" * 50

        when:
        def actual = validator.validate(target)

        then:
        actual == [] as Set
    }

    @Unroll
    def "異常系_answer3でバリデーションエラー_testName"() {
        given:
        target.answer.answer3 = input

        when:
        def actual = validator.validate(target)

        then:
        actual.size() == 1
        actual[0].message == expected
        where:
        testName | input    || expected
        "null"   | null     || "必須パラメータです"
        "空文字"    | ""       || "必須パラメータです"
        "51文字"   | "c" * 51 || "最大50文字で設定してください"
    }

    def "正常系_answer4が50文字"() {
        given:
        target.answer.answer4 = "a" * 50

        when:
        def actual = validator.validate(target)

        then:
        actual == [] as Set
    }

    @Unroll
    def "異常系_answer4でバリデーションエラー_#testName"() {
        given:
        target.answer.answer4 = input

        when:
        def actual = validator.validate(target)

        then:
        actual.size() == 1
        actual[0].message == expected

        where:
        testName | input    || expected
        "null"   | null     || "必須パラメータです"
        "空文字"    | ""       || "必須パラメータです"
        "201文字"  | "c" * 51 || "最大50文字で設定してください"
    }

    @Unroll
    def "正常系_correctNumber_正常値網羅_#testName"() {
        given:
        target.answer.correctNumber = input

        when:
        def actual = validator.validate(target)

        then:
        actual == [] as Set

        where:
        testName | input
        "1"      | 1
        "2"      | 2
        "3"      | 3
        "4"      | 4
    }

    def "異常系_collectNumberがnull"() {
        given:
        target.answer.correctNumber = null

        when:
        def actual = validator.validate(target)

        then:
        actual.size() == 2
        actual[0].message == "1,2,3,4 のいずれかを指定してください" || "必須パラメータです"
        actual[1].message == "1,2,3,4 のいずれかを指定してください" || "必須パラメータです"

    }

    @Unroll
    def "異常系_correctNumber__#testName"() {
        given:
        target.answer.correctNumber = input

        when:
        def actual = validator.validate(target)

        then:
        actual.size() == 1
        actual[0].message == expected

        where:
        testName | input || expected
        "0"      | 0     || "1,2,3,4 のいずれかを指定してください"
        "5"      | 5     || "1,2,3,4 のいずれかを指定してください"
    }

}
