package com.elite.miko.quiz.presentation.validator

import com.elite.miko.quiz.domain.model.consts.QuizStatus
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.ConstraintValidatorContext

class QuizStatusValidatorSpec extends Specification {

    QuizStatusValidator target

    def setup() {
        target = new QuizStatusValidator()
    }

    def "正常系_isValid_バリデーションエラー無し"() {
        expect:
        def actual = target.isValid(QuizStatus.ENABLED, Mock(ConstraintValidatorContext))
        actual
    }

    @Unroll
    def "異常系_isValid_バリデーションエラー_#testName"() {
        expect:
        def actual = target.isValid(quizStatus, Mock(ConstraintValidatorContext))
        !actual
        where:
        testName  | quizStatus
        "null"    | null
        "INVALID" | QuizStatus.INVALID
    }
}
