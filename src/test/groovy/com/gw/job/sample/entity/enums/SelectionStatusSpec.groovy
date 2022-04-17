package com.gw.job.sample.entity.enums

import spock.lang.Specification
import spock.lang.Unroll

class SelectionStatusSpec extends Specification {

    @Unroll
    def "正常系 map #testName"() {
        when:
        def actual = SelectionStatus.map(val)
        then:
        actual == expected
        where:
        testName           | val || expected
        "選考前"              | "0" || SelectionStatus.BEFORE
        "選考中"              | "1" || SelectionStatus.NOW
        "採用"               | "2" || SelectionStatus.RECRUITMENT
        "不採用"              | "9" || SelectionStatus.NOT_RECRUITMENT
        "不正値の場合Invalidになる" | "10" || SelectionStatus.INVALID
    }

    @Unroll
    def "正常系 of #testName"() {
        when:
        def actual = SelectionStatus.of(val)
        then:
        actual == expected
        where:
        testName | val || expected
        "選考前"    | "0" || SelectionStatus.BEFORE
        "選考中"    | "1" || SelectionStatus.NOW
        "採用"     | "2" || SelectionStatus.RECRUITMENT
        "不採用"    | "9" || SelectionStatus.NOT_RECRUITMENT
    }

    def "異常系 of 不正な値の場合例外が発生する"() {
        when:
        SelectionStatus.of("10")
        then:
        def exception = thrown(IllegalArgumentException.class)
        exception.getMessage() == "selection status is invalid value"
    }
}
