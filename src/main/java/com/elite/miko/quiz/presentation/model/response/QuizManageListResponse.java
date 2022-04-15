package com.elite.miko.quiz.presentation.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * クイズ問題一覧を返却するレスポンス
 */
@Data
@Builder
@Schema(description = "クイズ管理用情報取得結果")
public class QuizManageListResponse {

    @Schema(description = "総件数", example = "10", required = true)
    private long total;

    @Schema(description = "取得開始位置", minimum = "1", example = "10", required = true)
    private long start;

    @Schema(description = "取得件数", minimum = "10", maximum = "100", example = "10", required = true)
    private int count;

    @Schema(description = "クイズ問題一覧", required = true)
    private List<QuizAll> quizList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "QuizQuestionResponse.QuizQuestion", description = "クイズ一覧取得結果")
    public static class QuizAll {

        @Schema(description = "クイズID", example = "1", required = true)
        private Long quizId;

        @Schema(description = "問題文", example = "さくらみこの挨拶といえば？", required = true)
        private String question;

        @Schema(description = "解説文", example = "さくらみこの挨拶はにゃっはろー", required = true)
        private String commentary;

        @Schema(description = "クイズの状態", example = "有効", required = true)
        private String quizStatus;

        @Schema(description = "クイズの管理用回答情報", required = true)
        private QuizManageAnswer answer;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @Schema(name = "QuizQuestionResponse.QuizQuestion,QuizAnswer", description = "クイズの管理用回答情報")
        public static class QuizManageAnswer {

            @Schema(description = "回答ID", example = "1", required = true)
            private Long answerId;

            @Schema(description = "回答1", example = "にゃっはろー", required = true)
            private String answer1;

            @Schema(description = "回答2", example = "こんこんきーつね", required = true)
            private String answer2;

            @Schema(description = "回答3", example = "こんやっぴー", required = true)
            private String answer3;

            @Schema(description = "回答4", example = "ゆびゆびー", required = true)
            private String answer4;

            @Schema(description = "正答番号 1から4の数字", example = "1", required = true)
            private int correctNumber;
        }
    }
}
