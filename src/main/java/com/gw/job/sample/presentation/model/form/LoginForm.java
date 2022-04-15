package com.gw.job.sample.presentation.model.form;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "ログインリクエスト")
public class LoginForm {

    @NotBlank(message = "必須パラメータです")
    @Size(max = 12, message = "最大12文字で設定してください")
    @Schema(description = "アカウントID", example = "root", required = true)
    private String accountId;

    @NotBlank(message = "必須パラメータです")
    @Schema(description = "パスワード", example = "password", required = true)
    private String password;
}
