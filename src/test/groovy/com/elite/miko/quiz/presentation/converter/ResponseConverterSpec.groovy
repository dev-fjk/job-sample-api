package com.elite.miko.quiz.presentation.converter

import com.elite.miko.quiz.domain.model.result.QuizManageResult
import com.elite.miko.quiz.domain.model.result.QuizQuestionResult
import com.elite.miko.quiz.infrastructure.model.entity.Quiz
import com.elite.miko.quiz.presentation.model.response.QuizManageListResponse
import com.elite.miko.quiz.presentation.model.response.QuizQuestionListResponse
import spock.lang.Specification

class ResponseConverterSpec extends Specification {

    ResponseConverter target

    def quizConverter = Mock(QuizConverter)

    def setup() {
        target = new ResponseConverter(quizConverter)
    }

    def "正常系_convert(QuizQuestionResult)"() {
        given:
        def quizList = (1..3).collect() {
            return new Quiz()
        }
        def quizQuestionResult = QuizQuestionResult.builder()
                .quizList(quizList).build()

        def quizQuestionList = (1..3).collect() {
            return QuizQuestionListResponse.QuizQuestion.builder().build()
        }

        when:
        def actual = target.convert(quizQuestionResult)

        then:
        1 * quizConverter.convert(quizQuestionResult) >> quizQuestionList
        actual == new QuizQuestionListResponse(
                3, quizQuestionList
        )
    }

    def "異常系_convert(QuizQuestionResult)_引数がnull"() {
        when:
        target.convert(null as QuizQuestionResult)

        then:
        thrown(NullPointerException)
    }

    def "正常系_convert(QuizManageResult)"() {
        given:
        def quizList = (1..3).collect() {
            return new Quiz()
        }
        def quizManagementResult = QuizManageResult
                .builder().start(0)
                .total(3)
                .quizList(quizList)
                .build()

        def quizAll = (1..3).collect() {
            return new QuizManageListResponse.QuizAll()
        }

        when:
        def actual = target.convert(quizManagementResult)

        then:
        1 * quizConverter.convert(quizManagementResult) >> quizAll
        actual == QuizManageListResponse.builder()
                .total(3)
                .start(1)
                .count(3)
                .quizList(quizAll)
                .build()
    }

    def "異常系_convert(QuizManageResult)_引数がnull"() {
        when:
        target.convert(null as QuizManageResult)

        then:
        thrown(NullPointerException)
    }
}
