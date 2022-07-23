package com.gw.job.sample.entity.selector;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * レジュメ一覧取得時の クエリパラメータ
 */
@Data
@Schema(description = "レジュメ一覧検索条件")
@ParameterObject
public class ResumeListSelector {

    @Parameter(description = "取得開始位置 1以上の値を設定",
            schema = @Schema(format = "int32", defaultValue = "1"))
    private int start = 1;

    @Parameter(description = "取得件数 0~100の範囲で値を設定",
            schema = @Schema(format = "int32", defaultValue = "20"))
    private int count = 20;

    @Parameter(description = "ユーザーIDリスト カンマ区切りで複数指定可能",
            schema = @Schema(type = "array", format = "int64"))
    private List<Long> userIds = Collections.emptyList();

    @Parameter(schema = @Schema(type = "array", format = "int32"),
            description = "選考状況 カンマ区切りで複数指定可能　\n" +
                    "* `1` - 選考前 \n" +
                    "* `2` - 選考中 \n" +
                    "* `3` - 採用 \n" +
                    "* `4` - 不採用 \n")
    private List<Integer> status = Collections.emptyList();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Parameter(description = "入社日 開始指定", schema = @Schema(type = "string"))
    private LocalDate entryDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Parameter(description = "入社日 終了指定 entryDateFromより過去日は設定不可", schema = @Schema(type = "string"))
    private LocalDate entryDateTo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Parameter(description = "応募日 開始指定", schema = @Schema(type = "string"))
    private LocalDate createdDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Parameter(description = "応募日 終了指定 createdDateFromより過去日は設定不可 " +
            "2022-07-15を指定した場合 2022-07-15 23:59:59までに応募したユーザーが対象となる", schema = @Schema(type = "string"))
    private LocalDate createdDateTo;
}
