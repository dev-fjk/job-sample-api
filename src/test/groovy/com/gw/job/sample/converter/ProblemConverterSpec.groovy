package com.gw.job.sample.converter

import com.gw.job.sample.entity.response.ProblemResponse
import com.gw.job.sample.exception.RepositoryControlException
import com.gw.job.sample.exception.ResourceAlreadyExistException
import com.gw.job.sample.exception.ResourceNotFoundException
import com.gw.job.sample.exception.ValidationException
import org.hibernate.validator.internal.engine.path.PathImpl
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException

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
                .detail("test は 1 から 10 の間にしてください")
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
                .detail("test は 1 から 10 の間にしてください")
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
        "エラー単体"  | ["test は 1 から 10 の間にしてください"]
                          || "test は 1 から 10 の間にしてください"
        "エラー複数"  | ["test は 1 から　10 の間にしてください", "test は 1 から　10 の間にしてください"]
                          || "test は 1 から　10 の間にしてください,test は 1 から　10 の間にしてください"

    }

    def "正常系_convert(ResourceNotFoundException)_404エラー"() {
        given:
        def exception = new ResourceNotFoundException("リソースが見つかりません")

        when:
        def actual = target.convert(exception)

        then:
        actual == ProblemResponse.builder()
                .title("リクエストされたリソースは見つかりませんでした")
                .status(HttpStatus.NOT_FOUND.value())
                .detail("リソースが見つかりません")
                .build()
    }

    def "正常系_convert(ResourceAlreadyExistException)_409エラー"() {
        given:
        def exception = new ResourceAlreadyExistException("リソースが存在します")

        when:
        def actual = target.convert(exception)

        then:
        actual == ProblemResponse.builder()
                .title("リソースが既に存在しています")
                .status(HttpStatus.CONFLICT.value())
                .detail("リソースが存在します")
                .build()
    }

    def "正常系_convert(RepositoryControlException)_500エラー"() {
        given:
        def exception = new RepositoryControlException("リソースが更新できませんでした")

        when:
        def actual = target.convert(exception)

        then:
        actual == ProblemResponse.builder()
                .title("データの更新で失敗しました")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail("リソースが更新できませんでした")
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
