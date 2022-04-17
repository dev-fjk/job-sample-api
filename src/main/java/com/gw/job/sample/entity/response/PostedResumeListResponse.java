package com.gw.job.sample.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
@Schema(description = "企業に応募しているレジュメ一覧レスポンス")
public class PostedResumeListResponse {

    @Schema(description = "総件数", example = "100")
    private int total;

    @Schema(description = "取得開始位置", example = "1")
    private int start;

    @Schema(description = "取得件数", example = "20")
    private int count;

    /**
     * レジュメ一覧
     */
    private List<PostedResume> resumes;

    @Data
    @Schema(description = "企業に応募しているレジュメ")
    public static class PostedResume {

        @Schema(description = "ユーザーID", example = "1")
        private long userId;

        @Schema(description = "苗字", example = "苗字")
        private String lastName;

        @Schema(description = "名前", example = "名前")
        private String firstName;

        @Schema(description = "生年月日 yyyy-MM-ddの形式", example = "1990-10-10")
        private String birthDate;

        @Schema(description = "職務要約", example = "プログラマーとして働いていました。")
        private String jobDescription;

        @Schema(description = "応募日時 ISO8601形式の日付文字列", example = "2022-04-30T12:00:00")
        private String postedDate;

        @Schema(description = "選考状況ID 0:採用前,1:選考中,2:採用,9:不採用", example = "1")
        private String selectionStatusId;

        @Schema(description = "選考状況表示名", example = "選考中")
        private String selectionStatusName;
    }
}
