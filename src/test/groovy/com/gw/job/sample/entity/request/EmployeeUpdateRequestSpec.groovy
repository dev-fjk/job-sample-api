package com.gw.job.sample.entity.request

import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Validation
import javax.validation.Validator

/**
 * EmployeeAddRequestSpec側で親のフィールドテスト済
 */
class EmployeeUpdateRequestSpec extends Specification {

    EmployeeUpdateRequest target

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator()

    def setup() {
        // バリデーションエラーにならないインスタンスを作成しておく
        target = new EmployeeUpdateRequest(
                lastName: "テスト",
                firstName: "太郎",
                departmentCode: 1,
                updatedBy: "test"
        )
    }

    def "updatedBy 最大文字数"() {
        given:
        target.updatedBy = "x" * 30

        when:
        def actual = validator.validate(target)

        then:
        actual == [] as Set
    }

    @Unroll
    def "updatedBy バリデーションエラー #testName"() {
        given:
        target.updatedBy = input

        when:
        def actual = validator.validate(target)

        then:
        actual[0].getMessage() == expected

        where:
        testName | input    || expected
        "null"   | null     || "null は許可されていません"
        "31文字"   | "x" * 31 || "0 から 30 の間のサイズにしてください"
    }
}
