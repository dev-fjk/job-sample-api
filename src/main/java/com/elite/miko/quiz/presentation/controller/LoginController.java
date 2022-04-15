package com.elite.miko.quiz.presentation.controller;

import com.elite.miko.quiz.application.common.constant.OpenApiConstant;
import com.elite.miko.quiz.domain.service.AdminAccountService;
import com.elite.miko.quiz.presentation.model.form.LoginForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(path = LoginController.BASE_PATH)
@RequiredArgsConstructor
@Tag(name = LoginController.BASE_PATH, description = "管理用ページ向けの認証を行うAPI")
public class LoginController {

    public static final String BASE_PATH = "/miko/v1/login/";

    private final AdminAccountService adminAccountService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "管理者用ページへ遷移するためのログイン処理を行う")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = LoginForm.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ログインに成功した", content = @Content),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "401", ref = OpenApiConstant.LOGIN_FAILURE),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> isLogin(@Validated @RequestBody LoginForm loginForm) {

        final String jsonWebToken = adminAccountService.login(loginForm.getAccountId(), loginForm.getPassword());

        // 生成したTokenをAuthorizationヘッダーに設定して返却
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(QuizAuthorizationInterceptor.AUTHORIZATION_HEADER, jsonWebToken);
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.ok().headers(responseHeaders).build();
    }
}
