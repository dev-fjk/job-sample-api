package com.gw.job.sample.controller;

import com.gw.job.sample.config.OpenApiConstant;
import com.gw.job.sample.entity.request.ResumeListQueryParameter;
import com.gw.job.sample.entity.response.PostedResumeListResponse;
import com.gw.job.sample.entity.response.ResumeResponse;
import com.gw.job.sample.service.interfaces.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping(path = ResumeController.BASE_PATH)
@RequiredArgsConstructor
@Tag(name = ResumeController.BASE_PATH, description = "ユーザーの職務経歴情報管理用API")
public class ResumeController {

    public static final String BASE_PATH = "/resume/v1";

    private final ResumeService resumeService;

    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "レジュメ情報を取得する")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "レジュメ取得結果 取得失敗時は空のJson Bodyを返す", content = @Content(
                    schema = @Schema(implementation = ResumeResponse.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE
            )),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> getUserResume(@PathVariable("userId") long userId) {
        log.info("request: {}", userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/companies/{cid}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "企業へ応募中のレジュメ一覧を取得する")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "レジュメ一覧取得結果", content = @Content(
                    schema = @Schema(implementation = PostedResumeListResponse.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE
            )),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "404", ref = OpenApiConstant.NOT_FOUND),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> getPostedUserResumeList(
            @PathVariable("cid") long cid, @Validated @ModelAttribute ResumeListQueryParameter parameters) {
        log.info("request: {} {}", cid, parameters);
        return ResponseEntity.ok().build();
    }
}
