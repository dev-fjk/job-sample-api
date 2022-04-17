package com.gw.job.sample.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 日付変換を行うUtilクラス
 */
public class DateFormatterUtil {

    private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ISO_DATE;
    private static final DateTimeFormatter ISO_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    /**
     * ISO8601拡張形式の文字列を返す
     *
     * @param src ISO8601拡張形式の日付
     * @return yyyy-MM-dd形式の文字列
     */
    public static String isoDateToString(LocalDate src) {
        return src.format(ISO_DATE_FORMATTER);
    }

    /**
     * ISO8601拡張形式のLocalDateを返す
     *
     * @param src yyyy-MM-dd形式の文字列
     * @return yISO8601拡張形式の日付
     */
    public static LocalDate isoStringDateToLocalDate(String src) {
        return LocalDate.parse(src, DateTimeFormatter.ISO_DATE);
    }
}
