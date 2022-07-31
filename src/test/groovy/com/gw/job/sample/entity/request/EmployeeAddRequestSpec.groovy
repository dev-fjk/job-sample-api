package com.gw.job.sample.entity.request


import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Validation
import javax.validation.Validator
import java.time.LocalDate

/**
 * BaseRequestの値のテストも行う
 */
class EmployeeAddRequestSpec extends Specification {

    EmployeeAddRequest target

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator()

    def setup() {
        // バリデーションエラーにならないインスタンスを作成しておく
        target = new EmployeeAddRequest(
                lastName: "テスト",
                firstName: "太郎",
                departmentCode: 1,
                entryDate: LocalDate.of(2022, 7, 20),
                createdBy: "test"
        )
    }

    def "lastName 最大文字数"() {
        given:
        target.lastName = "x" * 15

        when:
        def actual = validator.validate(target)

        then:
        actual == [] as Set
    }

    @Unroll
    def "lastName バリデーションエラー #testName"() {
        given:
        target.lastName = input

        when:
        def actual = validator.validate(target)

        then:
        actual[0].getMessage() == expected

        where:
        testName | input    || expected
        "null"   | null     || "空要素は許可されていません"
        "空文字"    | ""       || "空要素は許可されていません"
        "16文字"   | "x" * 16 || "0 から 15 の間のサイズにしてください"
    }

    def "firstName 最大文字数"() {
        given:
        target.firstName = "x" * 15

        when:
        def actual = validator.validate(target)

        then:
        actual == [] as Set
    }

    @Unroll
    def "firstName バリデーションエラー　#testName"() {
        given:
        target.firstName = input

        when:
        def actual = validator.validate(target)

        then:
        actual[0].getMessage() == expected

        where:
        testName | input    || expected
        "null"   | null     || "空要素は許可されていません"
        "空文字"    | ""       || "空要素は許可されていません"
        "16文字"   | "x" * 16 || "0 から 15 の間のサイズにしてください"
    }

    @Unroll
    def "departmentCode 境界値 #testName"() {
        given:
        target.departmentCode = input

        when:
        def actual = validator.validate(target)

        then:
        actual == [] as Set

        where:
        testName | input
        "1"      | 1
        "5"      | 5
    }

    @Unroll
    def "departmentCode バリデーションエラー　#testName"() {
        given:
        target.departmentCode = input

        when:
        def actual = validator.validate(target)

        then:
        actual[0].getMessage() == expected

        where:
        testName | input || expected
        "null"   | null  || "null は許可されていません"
        "最小値未満"  | 0     || "1 から 5 の間にしてください"
        "最大値超え"  | 6     || "1 から 5 の間にしてください"
    }

    def "entryDate"() {
        given:
        target.entryDate = LocalDate.of(2022, 7, 30)

        when:
        def actual = validator.validate(target)

        then:
        actual == [] as Set
    }

    def "entryDate バリデーションエラー"() {
        given:
        target.entryDate = null

        when:
        def actual = validator.validate(target)

        then:
        actual[0].getMessage() == "null は許可されていません"
    }

    def "createdBy 最大文字数"() {
        given:
        target.createdBy = "x" * 30

        when:
        def actual = validator.validate(target)

        then:
        actual == [] as Set
    }

    @Unroll
    def "createdBy バリデーションエラー #testName"() {
        given:
        target.createdBy = input

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
