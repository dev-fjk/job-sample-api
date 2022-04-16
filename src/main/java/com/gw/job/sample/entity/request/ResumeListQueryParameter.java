package com.gw.job.sample.entity.request;

import com.gw.job.sample.entity.enums.SelectionStatus;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import javax.validation.constraints.AssertTrue;
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

    private static final String ALL = "*";

    @Parameter(description = "取得開始位置", example = "1")
    private int start = 1;

    @Parameter(description = "取得件数", example = "20")
    private int count = 20;

    @Parameter(description = "ユーザーIDリスト カンマ区切りで複数指定可能 デフォルト:空",
            schema = @Schema(type = "list", format = "int64"))
    private Set<Long> userIdList = Collections.emptySet();

    @Parameter(description = "選考状況 カンマ区切りの文字列か *を設定 0:採用前,1:選考中,2:採用,9:不採用, *:全指定 デフォルト: *")
    private String selectionStatus = ALL;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Parameter(description = "応募日時の絞り込み開始時刻 ISO8601拡張形式", example = "2022-04-30T00:00:00")
    private LocalDateTime postedDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Parameter(description = "応募日時の絞り込み終了時刻 ISO8601拡張形式", example = "2022-04-30T23:59:59")
    private LocalDateTime postedDateTo;

    /**
     * SelectionStatusの値チェック
     *
     * @return trueの場合正常値
     */
    @Hidden
    @AssertTrue(message = "0,1,2,9 か *を指定してください")
    public boolean isValidSelectionStatus() {
        return ALL.equals(this.selectionStatus) || Arrays.stream(SelectionStatus.values())
                .map(SelectionStatus::getValue)
                .anyMatch(v -> this.selectionStatus.equals(v));
    }
}
