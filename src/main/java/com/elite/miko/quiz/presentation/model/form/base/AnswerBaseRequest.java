package com.elite.miko.quiz.presentation.model.form.base;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import java.util.stream.IntStream;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AnswerBaseRequest {

    @NotBlank(message = "必須パラメータです")
    @Size(max = 50, message = "最大50文字で設定してください")
    @Schema(description = "回答1", example = "にゃっはろー", required = true)
    private String answer1;

    @NotBlank(message = "必須パラメータです")
    @Size(max = 50, message = "最大50文字で設定してください")
    @Schema(description = "回答2", example = "こんこんきーつね", required = true)
    private String answer2;

    @NotBlank(message = "必須パラメータです")
    @Size(max = 50, message = "最大50文字で設定してください")
    @Schema(description = "回答3", example = "こんやっぴー", required = true)
    private String answer3;

    @NotBlank(message = "必須パラメータです")
    @Size(max = 50, message = "最大50文字で設定してください")
    @Schema(description = "回答4", example = "ゆびゆびー", required = true)
    private String answer4;

    @NotNull(message = "必須パラメータです")
    @Schema(description = "正答番号 1から4のいずれかの数字を設定", example = "1", required = true)
    private Integer correctNumber;

    @AssertTrue(message = "1,2,3,4 のいずれかを指定してください")
    public boolean isCorrectNumber() {

        if (Objects.isNull(this.correctNumber)) {
            // Nullの場合 NPEで落ちるのでfalseを返す
            return false;
        }

        return IntStream.of(1, 2, 3, 4).anyMatch(this.correctNumber::equals);
    }
}
