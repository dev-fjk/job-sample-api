package com.elite.miko.quiz.presentation.controller

import com.elite.miko.quiz.application.exception.*
import com.elite.miko.quiz.presentation.converter.ProblemConverter
import com.elite.miko.quiz.presentation.model.response.ProblemResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.BindException
import spock.lang.Specification

import javax.validation.ConstraintViolationException
import java.nio.file.AccessDeniedException

class QuizExceptionHandlerSpec extends Specification {

    QuizExceptionHandler target

    def problemConverter = Mock(ProblemConverter)

    def setup() {
        target = new QuizExceptionHandler(problemConverter)
    }

    def "正常系_handleBindException"() {
        given:
        1 * problemConverter.convert(_ as BindException) >> ProblemResponse
                .builder().title("mock").build()
        when:
        def actual = target.handleBindException(Mock(BindException))

        then:
        verifyAll(actual) {
            statusCode == HttpStatus.BAD_REQUEST
            headers.getContentType() == MediaType.APPLICATION_PROBLEM_JSON
            body != null
        }
    }

    def "正常系_handleConstraintViolationException"() {
        given:
        1 * problemConverter.convert(_ as ConstraintViolationException) >> ProblemResponse
                .builder().title("mock").build()
        when:
        def actual = target.handleConstraintViolationException(
                Mock(ConstraintViolationException)
        )

        then:
        verifyAll(actual) {
            statusCode == HttpStatus.BAD_REQUEST
            headers.getContentType() == MediaType.APPLICATION_PROBLEM_JSON
            body != null
        }
    }

    def "正常系_handleValidationException"() {
        given:
        1 * problemConverter.convert(_ as ValidationException) >> ProblemResponse
                .builder().title("mock").build()
        when:
        def actual = target.handleValidationException(
                Mock(ValidationException)
        )

        then:
        verifyAll(actual) {
            statusCode == HttpStatus.BAD_REQUEST
            headers.getContentType() == MediaType.APPLICATION_PROBLEM_JSON
            body != null
        }
    }

    def "正常系_handleLoginFailureException"() {
        given:
        1 * problemConverter.convert(_ as LoginFailureException) >> ProblemResponse
                .builder().title("mock").build()
        when:
        def actual = target.handleLoginFailureException(
                Mock(LoginFailureException)
        )

        then:
        verifyAll(actual) {
            statusCode == HttpStatus.UNAUTHORIZED
            headers.getContentType() == MediaType.APPLICATION_PROBLEM_JSON
            body != null
        }
    }

    def "正常系_handleAccessDeniedException"() {
        given:
        1 * problemConverter.convert(_ as AccessDeniedException) >> ProblemResponse
                .builder().title("mock").build()
        when:
        def actual = target.handleAccessDeniedException(
                Mock(AccessDeniedException)
        )

        then:
        verifyAll(actual) {
            statusCode == HttpStatus.FORBIDDEN
            headers.getContentType() == MediaType.APPLICATION_PROBLEM_JSON
            body != null
        }
    }

    def "正常系_handleResourceNotFoundException"() {
        given:
        1 * problemConverter.convert(_ as ResourceNotFoundException) >> ProblemResponse
                .builder().title("mock").build()
        when:
        def actual = target.handleResourceNotFoundException(
                Mock(ResourceNotFoundException)
        )

        then:
        verifyAll(actual) {
            statusCode == HttpStatus.NOT_FOUND
            headers.getContentType() == MediaType.APPLICATION_PROBLEM_JSON
            body != null
        }
    }

    def "正常系_handleQuizNotEnoughCountException"() {
        given:
        1 * problemConverter.convert(_ as QuizNotEnoughCountException) >> ProblemResponse
                .builder().title("mock").build()
        when:
        def actual = target.handleQuizNotEnoughCountException(
                Mock(QuizNotEnoughCountException)
        )

        then:
        verifyAll(actual) {
            statusCode == HttpStatus.NOT_FOUND
            headers.getContentType() == MediaType.APPLICATION_PROBLEM_JSON
            body != null
        }
    }

    def "正常系_handleRepositoryControlException"() {
        given:
        1 * problemConverter.convert(_ as RepositoryControlException) >> ProblemResponse
                .builder().title("mock").build()
        when:
        def actual = target.handleRepositoryControlException(
                Mock(RepositoryControlException)
        )

        then:
        verifyAll(actual) {
            statusCode == HttpStatus.INTERNAL_SERVER_ERROR
            headers.getContentType() == MediaType.APPLICATION_PROBLEM_JSON
            body != null
        }
    }

    def "正常系_handleRuntimeException"() {
        given:
        1 * problemConverter.convert(_ as RuntimeException) >> ProblemResponse
                .builder().title("mock").build()
        when:
        def actual = target.handleRuntimeException(
                Mock(RuntimeException)
        )

        then:
        verifyAll(actual) {
            statusCode == HttpStatus.INTERNAL_SERVER_ERROR
            headers.getContentType() == MediaType.APPLICATION_PROBLEM_JSON
            body != null
        }
    }
}
