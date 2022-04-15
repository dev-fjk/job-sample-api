package com.gw.job.sample.entity.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * レジュメ一覧取得時の クエリパラメータ一覧
 */
@Data
@Schema(description = "レジュメ一覧検索条件")
@ParameterObject
public class ResumeListQueryParameter {

    @Parameter(description = "取得開始位置", example = "1")
    private int start = 1;

    @Parameter(description = "取得件数", example = "20")
    private int count = 20;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Parameter(description = "応募日時の絞り込み開始時刻 ISO8601拡張形式", example = "2022-04-30T00:00:00")
    private LocalDateTime postedDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Parameter(description = "応募日時の絞り込み終了時刻 ISO8601拡張形式", example = "2022-04-30T23:59:59")
    private LocalDateTime postedDateTo;
}
