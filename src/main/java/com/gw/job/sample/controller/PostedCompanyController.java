package com.gw.job.sample.controller;

import com.gw.job.sample.config.OpenApiConstant;
import com.gw.job.sample.entity.request.PostedUpdateRequest;
import com.gw.job.sample.entity.response.PostedResponse;
import com.gw.job.sample.entity.response.ResumeResponse;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 応募状況の操作を行うコントローラー
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = PostedCompanyController.BASE_PATH)
@RequiredArgsConstructor
@Tag(name = PostedCompanyController.BASE_PATH, description = "応募情報管理用API")
public class PostedCompanyController {

    public static final String BASE_PATH = "/posted-company/v1/";

    @GetMapping("/users/{userId}/companies/{cid}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "企業へ応募しているか返す")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "企業へ応募済みのユーザーか返す",
                    content = @Content(schema = @Schema(implementation = PostedResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> isPostedUser(@PathVariable("userId") long userId, @PathVariable("cid") long cid) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/{userId}/companies/{cid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "企業へ応募する")
    @ApiResponses({
            @ApiResponse(responseCode = "204", ref = OpenApiConstant.INSERTED_SUCCESS),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> postUser(@PathVariable("userId") long userId, @PathVariable("cid") long cid) {
        return ResponseEntity.noContent().build();
    }

    @PutMapping("companies/{cid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "企業がユーザーの採用状況を更新する")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = PostedUpdateRequest.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", ref = OpenApiConstant.UPDATED_SUCCESS),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> updatePostUser(@PathVariable("cid") long cid, @Validated @RequestBody PostedUpdateRequest request) {
        log.info("cid: {}, request : {}", cid, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/{userId}/companies/{cid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "ユーザーの応募情報を削除する")
    @ApiResponses({
            @ApiResponse(responseCode = "204", ref = OpenApiConstant.DELETED_SUCCESS),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> deletePosted(@PathVariable("userId") long userId, @PathVariable("cid") long cid) {
        return ResponseEntity.noContent().build();
    }
}
