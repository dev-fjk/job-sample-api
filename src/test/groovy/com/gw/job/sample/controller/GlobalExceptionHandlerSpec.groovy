package com.gw.job.sample.controller

import com.gw.job.sample.exception.RepositoryControlException
import com.gw.job.sample.exception.ResourceAlreadyExistException
import com.gw.job.sample.exception.ResourceNotFoundException
import com.gw.job.sample.exception.ValidationException
import org.hibernate.validator.internal.engine.path.PathImpl
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import spock.lang.Specification

import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException

class GlobalExceptionHandlerSpec extends Specification {

    GlobalExceptionHandler target

    def setup() {
        target = new GlobalExceptionHandler()
    }

    def "handleBindException"() {
        given:
        def ex = Mock(BindException)
        def fieldError = Mock(FieldError)

        and:
        fieldError.field >> "test"
        fieldError.defaultMessage >> "1 から 10 の間にしてください"
        fieldError.rejectedValue >> "11"

        ex.getFieldErrors() >> [fieldError, fieldError]

        when:
        def actual = target.handleBindException(ex)

        then:
        verifyAll(actual) {
            status == 400
            headers.get("Content-Type").contains("application/problem+json")
            verifyAll(body) {
                title == "リクエストされたパラメータは正しくありません"
                status == 400
                detail == "test は 1 から 10 の間にしてください, test は 1 から 10 の間にしてください"
            }
        }
    }

    def "handleConstraintViolationException"() {
        given:
        def ex = Mock(ConstraintViolationException)
        def violations = Mock(ConstraintViolation)

        and:
        violations.getPropertyPath() >> PathImpl.createPathFromString("test")
        violations.getMessage() >> "1 から 10 の間にしてください"
        ex.getConstraintViolations() >> [violations]

        when:
        def actual = target.handleConstraintViolationException(ex)

        then:
        verifyAll(actual) {
            status == 400
            headers.get("Content-Type").contains("application/problem+json")
            verifyAll(body) {
                title == "リクエストされたパラメータは正しくありません"
                status == 400
                detail == "test は 1 から 10 の間にしてください"
            }
        }
    }

    def "handleValidationException"() {
        given:
        def ex = new ValidationException(["test は 1 から 10 の間にしてください", "test は 1 から 10 の間にしてください"])

        when:
        def actual = target.handleValidationException(ex)

        then:
        verifyAll(actual) {
            status == 400
            headers.get("Content-Type").contains("application/problem+json")
            verifyAll(body) {
                title == "リクエストされたパラメータは正しくありません"
                status == 400
                detail == "test は 1 から 10 の間にしてください,test は 1 から 10 の間にしてください"
            }
        }
    }

    def "handleResourceNotFoundException"() {
        given:
        def ex = new ResourceNotFoundException("test")

        when:
        def actual = target.handleResourceNotFoundException(ex)

        then:
        verifyAll(actual) {
            status == 404
            headers.get("Content-Type").contains("application/problem+json")
            verifyAll(body) {
                title == "リクエストされたリソースは見つかりませんでした"
                status == 404
                detail == "test"
            }
        }
    }

    def "handleResourceAlreadyExistException"() {
        given:
        def ex = new ResourceAlreadyExistException("test")

        when:
        def actual = target.handleResourceAlreadyExistException(ex)

        then:
        verifyAll(actual) {
            status == 409
            headers.get("Content-Type").contains("application/problem+json")
            verifyAll(body) {
                title == "リソースが既に存在しています"
                status == 409
                detail == "test"
            }
        }
    }

    def "handleRepositoryControlException"() {
        given:
        def ex = new RepositoryControlException("test")

        when:
        def actual = target.handleRepositoryControlException(ex)

        then:
        verifyAll(actual) {
            status == 500
            headers.get("Content-Type").contains("application/problem+json")
            verifyAll(body) {
                title == "データの更新に失敗しました"
                status == 500
                detail == "test"
            }
        }
    }

    def "handleRuntimeException"() {
        given:
        def ex = new RuntimeException("test")

        when:
        def actual = target.handleRuntimeException(ex)

        then:
        verifyAll(actual) {
            status == 500
            headers.get("Content-Type").contains("application/problem+json")
            verifyAll(body) {
                title == "予期しないエラーが発生しました"
                status == 500
                detail == "test"
            }
        }
    }
}
