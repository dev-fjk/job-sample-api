package com.gw.job.sample.utility

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class DateFormatterUtilSpec extends Specification {

    def "正常系 isoDateToString ISO8601拡張形式の文字列に変換される"() {
        expect:
        def actual = DateFormatterUtil.isoDateToString(LocalDate.of(2022, 4, 30))
        actual == "2022-04-30"
    }

    def "正常系 isoStringDateToLocalDate ISO8601拡張形式の文字列からLocalDateへ変換される"() {
        expect:
        def actual = DateFormatterUtil.isoStringDateToLocalDate("2022-04-30")
        actual == LocalDate.of(2022, 4, 30)
    }

    @Unroll
    def "異常系 isoStringDateToLocalDate 異常な日付文字列の場合 例外が発生 #testName"() {
        given:
        def src = input
        when:
        DateFormatterUtil.isoStringDateToLocalDate(src)
        then:
        def exception = thrown(IllegalArgumentException)
        exception.getMessage() == "日付の変換に失敗しました"
        where:
        testName       | input
        "yyyy-MM-dd以外" | "20220430"
        "不正な日付"        | "2022-04-99"
    }
}
