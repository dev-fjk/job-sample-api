package com.gw.job.sample.entity.request;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import javax.validation.constraints.AssertTrue;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * レジュメ一覧取得時の クエリパラメータ
 */
@Data
@Schema(description = "レジュメ一覧検索条件")
@ParameterObject
public class ResumeListQueryParameter {

    private static final String ALL = "*";

    @Parameter(description = "取得開始位置 1以上の値を設定 デフォルト: 1", example = "1")
    private int start = 1;

    @Parameter(description = "取得件数 0~100の範囲で値を設定 デフォルト: 20", example = "20")
    private int count = 20;

    @Parameter(description = "ユーザーIDリスト カンマ区切りで複数指定可能 デフォルト:空",
            schema = @Schema(type = "list", format = "int64"))
    private Set<Long> userIdList = Collections.emptySet();

    @Parameter(description = "選考状況 カンマ区切りの数値か *を設定 0:採用前,1:選考中,2:採用,9:不採用, *:全指定 デフォルト: *")
    private String selectionStatus = ALL;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Parameter(description = "応募日時の絞り込み開始時刻 ISO8601拡張形式",
            schema = @Schema(type = "string"), example = "2022-04-30T00:00:00")
    private LocalDateTime postedDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Parameter(description = "応募日時の絞り込み終了時刻 ISO8601拡張形式",
            schema = @Schema(type = "string"), example = "2022-04-30T23:59:59")
    private LocalDateTime postedDateTo;

    /**
     * postedDateFromとpostedDateToの前後関係をチェックする
     *
     * @return fromとtoが同じ時刻 or fromの方が前の時刻ならtrue
     */
    @Hidden
    @AssertTrue(message = "応募日時(開始) より応募日時(終了)は 遅い日時を設定してください")
    public boolean isValidPostedDateLange() {
        return this.isValidDateTimeLange(this.postedDateFrom, this.postedDateTo);
    }

    /**
     * 日時の前後関係を返す
     *
     * @param from 開始指定
     * @param to   終了指定
     * @return true:問題なし false: 問題あり
     */
    private boolean isValidDateTimeLange(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null) {
            return true;
        }
        return to.isBefore(from) || from.equals(to);
    }
}
