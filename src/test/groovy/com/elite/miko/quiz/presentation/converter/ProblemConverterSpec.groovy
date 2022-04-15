package com.elite.miko.quiz.presentation.converter

import com.elite.miko.quiz.application.exception.*
import com.elite.miko.quiz.presentation.model.response.ProblemResponse
import org.hibernate.validator.internal.engine.path.PathImpl
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import java.nio.file.AccessDeniedException

class ProblemConverterSpec extends Specification {

    ProblemConverter target

    def setup() {
        target = new ProblemConverter()
    }

    def "正常系_convert(BindException)_400エラー"() {
        given:
        def fieldError = Mock(FieldError)
        def exception = Mock(BindException)

        and:
        fieldError.field >> "test"
        fieldError.defaultMessage >> "1 から 10 の間にしてください"
        fieldError.rejectedValue >> "11"
        exception.getFieldErrors() >> [fieldError]

        when:
        def actual = target.convert(exception)

        then:
        actual == ProblemResponse.builder()
                .title("リクエストされたパラメータは正しくありません")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail("test は 1 から 10 の間にしてください: 11")
                .build()
    }

    def "正常系_convert(ConstraintViolationException)_400エラー"() {
        given:
        def violation = Mock(ConstraintViolation)
        def exception = Mock(ConstraintViolationException)

        and:
        violation.getPropertyPath() >> PathImpl.createPathFromString("test")
        violation.message >> "1 から 10 の間にしてください"
        violation.invalidValue >> "11"
        exception.constraintViolations >> Set.of(violation)

        when:
        def actual = target.convert(exception)

        then:
        actual == ProblemResponse.builder()
                .title("リクエストされたパラメータは正しくありません")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail("test は 1 から 10 の間にしてください: 11")
                .build()
    }

    @Unroll
    def "正常系_convert(ValidationException)_400エラー_#testName"() {
        given:
        def exception = new ValidationException(errors)

        when:
        def actual = target.convert(exception)

        then:
        actual == ProblemResponse.builder()
                .title("リクエストされたパラメータは正しくありません")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(expected)
                .build()

        where:
        testName | errors || expected
        "エラー単体"  | ["test は 1 から 10 の間にしてください: 11"]
                          || "test は 1 から 10 の間にしてください: 11"
        "エラー複数"  | ["test は 1 から　10 の間にしてください: 11", "test は 1 から　10 の間にしてください: 12"]
                          || "test は 1 から　10 の間にしてください: 11,test は 1 から　10 の間にしてください: 12"

    }

    def "正常系_convert(LoginFailureException)_401エラー"() {
        given:
        def exception = new LoginFailureException("ログインに失敗しました")

        when:
        def actual = target.convert(exception)

        then:
        actual == ProblemResponse.builder()
                .title("認証に失敗しました")
                .status(HttpStatus.UNAUTHORIZED.value())
                .detail("ログインに失敗しました")
                .build()
    }

    def "正常系_convert(AccessDeniedException)_403エラー"() {
        given:
        def exception = new AccessDeniedException("権限がありません")

        when:
        def actual = target.convert(exception)

        then:
        actual == ProblemResponse.builder()
                .title("権限がありません")
                .status(HttpStatus.FORBIDDEN.value())
                .detail("アクセスが拒否されました")
                .build()
    }

    def "正常系_convert(ResourceNotFoundException)_404エラー"() {
        given:
        def exception = new ResourceNotFoundException("クイズが見つかりません")

        when:
        def actual = target.convert(exception)

        then:
        actual == ProblemResponse.builder()
                .title("リクエストされたリソースは見つかりませんでした")
                .status(HttpStatus.NOT_FOUND.value())
                .detail("クイズが見つかりません")
                .build()
    }

    def "正常系_convert(QuizNotEnoughCountException)_404エラー"() {
        given:
        def exception = new QuizNotEnoughCountException("クイズが指定された件数見つかりません")

        when:
        def actual = target.convert(exception)

        then:
        actual == ProblemResponse.builder()
                .title("リクエストされたリソースは見つかりませんでした")
                .status(HttpStatus.NOT_FOUND.value())
                .detail("クイズが指定された件数見つかりません")
                .build()
    }

    def "正常系_convert(RepositoryControlException)_500エラー"() {
        given:
        def exception = new RepositoryControlException("クイズが更新できませんでした")

        when:
        def actual = target.convert(exception)

        then:
        actual == ProblemResponse.builder()
                .title("データの更新で失敗しました")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail("クイズが更新できませんでした")
                .build()
    }

    def "正常系_convert(RuntimeException)_500エラー"() {
        given:
        def exception = new RuntimeException("データベースでエラーが発生しました")

        when:
        def actual = target.convert(exception)

        then:
        actual == ProblemResponse.builder()
                .title("予期しないエラーが発生しました")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail("データベースでエラーが発生しました")
                .build()
    }
}
