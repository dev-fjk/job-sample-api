package com.gw.job.sample.entity.enums

import spock.lang.Specification
import spock.lang.Unroll

class DepartmentCodeSpec extends Specification {

    @Unroll
    def "of #testName"() {
        when:
        def actual = DepartmentCode.of(input)

        then:
        actual == expected

        where:
        testName | input || expected
        "役員"     | 1     || DepartmentCode.OFFICER
        "総務部"    | 2     || DepartmentCode.AFFAIRS
        "経理部"    | 3     || DepartmentCode.ACCOUNTS
        "営業部"    | 4     || DepartmentCode.SALES
        "開発部"    | 5     || DepartmentCode.DEVELOPERS
    }

    def "of 引数が不正な値　#testName"() {
        when:
        DepartmentCode.of(null)

        then:
        thrown(IllegalArgumentException)

        where:
        testName | input
        "null"   | null
        "6"      | 6
    }
}
