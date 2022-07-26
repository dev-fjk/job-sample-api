package com.gw.job.sample.entity.selector;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 社員一覧取得時の クエリパラメータ
 */
@Data
@Schema(description = "社員一覧検索条件")
@ParameterObject
public class EmployeeListSelector {

    @Min(1)
    @Parameter(description = "取得開始位置 1以上の値を設定",
            schema = @Schema(format = "int32", defaultValue = "1"))
    private int start = 1;

    @Range(min = 0, max = 100)
    @Parameter(description = "取得件数 0~100の範囲で値を設定",
            schema = @Schema(format = "int32", defaultValue = "20"))
    private int count = 20;

    @Parameter(description = "社員IDリスト カンマ区切りで複数指定可能",
            schema = @Schema(type = "array", format = "int64"))
    private List<@NotNull @Min(1) Long> employeeIds = Collections.emptyList();

    @Parameter(schema = @Schema(type = "array", format = "int32"),
            description = "部門コード カンマ区切りで複数指定可能　\n" +
                    "* `1` - 役員 \n" +
                    "* `2` - 総務部 \n" +
                    "* `3` - 経理部 \n" +
                    "* `4` - 営業部 \n" +
                    "* `5` - 開発部 \n")
    private List<@NotNull @Range(min = 1, max = 5) Integer> departmentCodes = Collections.emptyList();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Parameter(description = "入社日 開始指定 yyyy-MM-ddの形式で指定", schema = @Schema(type = "string"))
    private LocalDate entryDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Parameter(description = "入社日 終了指定 yyyy-MM-ddの形式で指定  entryDateFromより過去日は設定不可",
            schema = @Schema(type = "string"))
    private LocalDate entryDateTo;

    /**
     * 入社日の相関チェックを行う
     *
     * @return true:問題なし, false:問題あり
     */
    @AssertTrue(message = "entryDateFromはentryDateToより過去日を設定してください")
    private boolean isValidEntryDateRange() {
        return isValidDateRange(entryDateFrom, entryDateTo);
    }

    /**
     * 日付の範囲チェックを行う
     *
     * @param from 開始指定日
     * @param to   終了指定日
     * @return true:問題なし, false:問題あり
     */
    private boolean isValidDateRange(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            return true;
        }
        return from.equals(to) || from.isBefore(to);
    }
}
