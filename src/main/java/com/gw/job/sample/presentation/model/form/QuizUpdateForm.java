package com.gw.job.sample.presentation.model.form;

import com.gw.job.sample.domain.model.consts.QuizStatus;
import com.gw.job.sample.application.common.annotation.QuizStatusConstraint;
import com.gw.job.sample.presentation.model.form.base.AnswerBaseRequest;
import com.gw.job.sample.presentation.model.form.base.QuizBaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Schema(description = "クイズ更新リクエスト")
public class QuizUpdateForm {

    @Valid
    @Schema(description = "更新するクイズ一覧", required = true)
    private List<UpdateQuiz> quizList;

    @Data
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = false)
    @Schema(description = "更新するクイズ情報")
    public static class UpdateQuiz extends QuizBaseRequest {

        @NotNull(message = "必須パラメータです")
        @Schema(description = "クイズID", example = "10", required = true)
        private Long quizId;

        @QuizStatusConstraint
        @Schema(description = "クイズステータス", example = "1", required = true)
        private QuizStatus quizStatus;

        @Valid
        @Schema(description = "クイズの回答", required = true)
        private UpdateAnswer answer;

        @Data
        @ToString(callSuper = true)
        @EqualsAndHashCode(callSuper = false)
        @Schema(description = "回答更新リクエスト")
        public static class UpdateAnswer extends AnswerBaseRequest {
            
            @NotNull(message = "必須パラメータです")
            @Schema(description = "回答ID", example = "101", required = true)
            private Long answerId;
        }
    }
}
