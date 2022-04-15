package com.elite.miko.quiz.presentation.model.form;

import com.elite.miko.quiz.presentation.model.form.base.AnswerBaseRequest;
import com.elite.miko.quiz.presentation.model.form.base.QuizBaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "クイズ追加リクエスト")
public class QuizAddForm extends QuizBaseRequest {

    @Valid
    @Schema(description = "クイズの回答", required = true)
    private AddAnswer answer;

    @Data
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = false)
    @Schema(description = "回答追加リクエスト")
    public static class AddAnswer extends AnswerBaseRequest {
    }
}
