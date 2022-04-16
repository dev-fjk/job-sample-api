package com.gw.job.sample.controller;

import com.gw.job.sample.config.OpenApiConstant;
import com.gw.job.sample.entity.request.ResumeAddRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(path = PostedCompanyController.BASE_PATH)
@RequiredArgsConstructor
@Tag(name = PostedCompanyController.BASE_PATH, description = "応募情報管理用API")
public class PostedCompanyController {

    public static final String BASE_PATH = "/posted-company/v1";


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "管理者用ページへ遷移するためのログイン処理を行う")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = ResumeAddRequest.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ログインに成功した", content = @Content),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> isLogin(@Validated @RequestBody ResumeAddRequest loginForm) {
        return ResponseEntity.ok().build();
    }
}
