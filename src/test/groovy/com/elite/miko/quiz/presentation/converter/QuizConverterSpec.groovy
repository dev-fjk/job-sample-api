package com.elite.miko.quiz.presentation.converter

import com.elite.miko.quiz.UnitTestBase
import com.elite.miko.quiz.domain.model.result.QuizManageResult
import com.elite.miko.quiz.domain.model.result.QuizQuestionResult
import com.elite.miko.quiz.infrastructure.model.entity.Answer
import com.elite.miko.quiz.infrastructure.model.entity.Quiz
import com.elite.miko.quiz.presentation.model.response.QuizManageListResponse
import com.elite.miko.quiz.presentation.model.response.QuizQuestionListResponse
import com.fasterxml.jackson.core.type.TypeReference
import org.modelmapper.ModelMapper
import spock.lang.Shared

class QuizConverterSpec extends UnitTestBase {

    QuizConverter target

    def modelMapper = Spy(ModelMapper)

    @Shared
    def entityDir = "/entity/"
    @Shared
    def responseDir = "/response/"

    def setup() {
        target = new QuizConverter(modelMapper)
    }

    def "正常系_convert(QuizQuestionResult)"() {
        given:
        def quizList = readJsonToObject(entityDir, "quizList",
                new TypeReference<List<Quiz>>() {}) as List<Quiz>
        def answerList = readJsonToObject(entityDir, "answerList",
                new TypeReference<List<Answer>>() {}) as List<Answer>

        def quizQuestionResult = QuizQuestionResult.builder()
                .quizList(quizList)
                .answerList(answerList).build()


        when:
        def actual = target.convert(quizQuestionResult)

        then:
        def expected = readJsonToObject(responseDir, "quizQuestionList",
                new TypeReference<List<QuizQuestionListResponse.QuizQuestion>>() {
                }) as List<QuizQuestionListResponse.QuizQuestion>

        actual == expected
    }

    def "正常系_convert(QuizManageResult)"() {
        given:
        def quizList = readJsonToObject(entityDir, "quizList",
                new TypeReference<List<Quiz>>() {}) as List<Quiz>
        def answerList = readJsonToObject(entityDir, "answerList",
                new TypeReference<List<Answer>>() {}) as List<Answer>

        def quizQuestionResult = QuizManageResult.builder()
                .quizList(quizList)
                .answerList(answerList).build()


        when:
        def actual = target.convert(quizQuestionResult)

        then:
        def expected = readJsonToObject(responseDir, "quizAllList",
                new TypeReference<List<QuizManageListResponse.QuizAll>>() {
                }) as List<QuizManageListResponse.QuizAll>

        actual == expected
    }

}
