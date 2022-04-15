package com.elite.miko.quiz.presentation.model.form.base;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public abstract class QuizBaseRequest {

    @NotBlank(message = "必須パラメータです")
    @Size(max = 200, message = "最大200文字で設定してください")
    @Schema(description = "問題文", example = "さくらみこの挨拶といえば？", required = true)
    private String question;

    @NotBlank(message = "必須パラメータです")
    @Size(max = 200, message = "最大200文字で設定してください")
    @Schema(description = "解説文", example = "さくらみこの挨拶はにゃっはろー", required = true)
    private String commentary;
}
