package com.gw.job.sample.entity.selector

import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Validation
import javax.validation.Validator
import java.time.LocalDate

class EmployeeListSelectorSpec extends Specification {

    EmployeeListSelector target
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator()

    def setup() {
        target = new EmployeeListSelector()
    }

    def "デフォルト設定の確認"() {
        when:
        target = new EmployeeListSelector()
        then:
        target.getStart() == 1
        target.getCount() == 20
        target.getEmployeeIds() == []
        target.getDepartmentCodes() == []
    }

    def "start バリデーションエラー"() {
        given:
        target.start = 0
        when:
        def actual = validator.validate(target)
        then:
        actual[0].getMessage() == "1 以上の値にしてください"
    }

    @Unroll
    def "count 境界値 #testName"() {
        given:
        target.count = input

        when:
        def actual = validator.validate(target)

        then:
        actual.isEmpty()

        where:
        testName | input
        "最小値"    | 0
        "最大値"    | 100
    }

    @Unroll
    def "count バリデーションエラー #testName"() {
        given:
        target.count = input

        when:
        def actual = validator.validate(target)

        then:
        actual[0].getMessage() == expected

        where:
        testName | input || expected
        "最小値未満"  | -1    || "0 から 100 の間にしてください"
        "最大値超え"  | 101   || "0 から 100 の間にしてください"
    }

    def "employeeIds"() {
        given:
        target.employeeIds = [1L, 2L]
        when:
        def actual = validator.validate(target)
        then:
        actual.isEmpty()
    }

    @Unroll
    def "employeeIds バリデーションエラー #testName"() {
        given:
        target.employeeIds = input

        when:
        def actual = validator.validate(target)

        then:
        actual[0].getMessage() == expected

        where:
        testName   | input      || expected
        "最小値未満が混在" | [1L, 0L]   || "1 以上の値にしてください"
        "nullが混在"  | [1L, null] || "null は許可されていません"
    }

    def "departmentCodes"() {
        given:
        target.departmentCodes = [1, 2, 3, 4, 5]
        when:
        def actual = validator.validate(target)
        then:
        actual.isEmpty()
    }

    @Unroll
    def "departmentCodesバリデーションエラー #testName"() {
        given:
        target.departmentCodes = input

        when:
        def actual = validator.validate(target)

        then:
        actual[0].getMessage() == expected

        where:
        testName   | input     || expected
        "最小値未満が混在" | [1, 0]    || "1 から 5 の間にしてください"
        "最大値超えが混在" | [1, 6]    || "1 から 5 の間にしてください"
        "nullが混在"  | [1, null] || "null は許可されていません"
    }

    @Unroll
    def "isValidEntryDateRange #testName"() {
        given:
        target.entryDateFrom = from
        target.entryDateTo = to

        when:
        def actual = validator.validate(target)

        then:
        actual.isEmpty()

        where:
        testName   | from                      | to
        "両方null"   | null                      | null
        "fromのみ設定" | LocalDate.of(2022, 7, 30) | null
        "toのみ設定"   | null                      | LocalDate.of(2022, 7, 30)
        "同日"       | LocalDate.of(2022, 7, 30) | LocalDate.of(2022, 7, 30)
        "fromが過去日" | LocalDate.of(2022, 7, 29) | LocalDate.of(2022, 7, 30)
    }

    def "isValidEntryDateRange toがfromより過去日"() {
        given:
        target.entryDateFrom = LocalDate.of(2022, 7, 30)
        target.entryDateTo = LocalDate.of(2022, 7, 29)

        when:
        def actual = validator.validate(target)

        then:
        actual[0].getMessage() == "entryDateFromはentryDateToより過去日を設定してください"
    }
}
