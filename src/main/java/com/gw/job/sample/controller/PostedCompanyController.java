package com.gw.job.sample.controller;

import com.gw.job.sample.config.OpenApiConstant;
import com.gw.job.sample.entity.request.PostedAddRequest;
import com.gw.job.sample.entity.request.PostedUpdateRequest;
import com.gw.job.sample.entity.response.PostedResponse;
import com.gw.job.sample.service.PostedCompanyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import javax.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 応募状況の操作を行うコントローラー
 */
@Validated
@RestController
@RequestMapping(path = PostedCompanyController.BASE_PATH)
@RequiredArgsConstructor
@Tag(name = PostedCompanyController.BASE_PATH, description = "応募情報管理用API")
public class PostedCompanyController {

    public static final String BASE_PATH = "/posted-company/v1/";
    private static final String GET_POSTED_COMPANY_PATH = BASE_PATH + "users/{userId}/companies/{companyId}";

    private final PostedCompanyService postedCompanyService;

    /**
     * 応募情報を取得する
     *
     * @param userId    ユーザーID
     * @param companyId 企業ID
     * @return {@link PostedResponse} が設定されたResponseEntity
     */
    @GetMapping("/users/{userId}/companies/{companyId}")
    @Operation(summary = "応募情報を取得する")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "応募情報",
                    content = @Content(schema = @Schema(implementation = PostedResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "404", ref = OpenApiConstant.NOT_FOUND),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<PostedResponse> isPostedUser(@PathVariable("userId") @Min(1) long userId,
                                                       @PathVariable("companyId") @Min(1) long companyId) {
        var response = postedCompanyService.findOne(userId, companyId);
        return ResponseEntity.ok(response);
    }

    /**
     * 企業へ応募する
     *
     * @param userId    ユーザーID
     * @param companyId 企業ID
     * @return {@link PostedResponse} 登録した応募情報がbodyに設定されたResponseEntity
     */
    @PostMapping("/users/{userId}/companies/{companyId}")
    @Operation(summary = "企業へ応募する")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = PostedAddRequest.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "登録した応募情報",
                    content = @Content(schema = @Schema(implementation = PostedResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "404", ref = OpenApiConstant.NOT_FOUND),
            @ApiResponse(responseCode = "409", ref = OpenApiConstant.CONFLICT),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<PostedResponse> postUser(@PathVariable("userId") @Min(1) long userId,
                                                   @PathVariable("companyId") @Min(1) long companyId,
                                                   @Validated @RequestBody PostedAddRequest request) {
        postedCompanyService.add(userId, companyId, request);
        var locationUri = UriComponentsBuilder.newInstance()
                        .path(GET_POSTED_COMPANY_PATH)
                        .uriVariables(Map.of("userId", userId))
                        .uriVariables(Map.of("companyId", companyId))
                        .build()
                        .toUri();
        return ResponseEntity.created(locationUri).build();
    }

    /**
     * 応募情報を更新する
     *
     * @param userId        ユーザーID
     * @param companyId     企業ID
     * @param request       応募更新リクエスト
     * @param bindingResult バリデーションエラー情報を保持したIF
     * @return {@link PostedResponse} 更新した応募情報をbodyに保持したResponseEntity
     */
    @PutMapping("/users/{userId}/companies/{companyId}")
    @Operation(summary = "応募情報を更新する")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = PostedUpdateRequest.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "更新した応募情報",
                    content = @Content(schema = @Schema(implementation = PostedResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )),
            @ApiResponse(responseCode = "204", ref = OpenApiConstant.UPDATED_SUCCESS),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "404", ref = OpenApiConstant.NOT_FOUND),
            @ApiResponse(responseCode = "409", ref = OpenApiConstant.CONFLICT),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<PostedResponse> updatePostUser(@PathVariable("userId") @Min(1) long userId,
                                                         @PathVariable("companyId") @Min(1) long companyId,
                                                         @Validated @RequestBody PostedUpdateRequest request,
                                                         BindingResult bindingResult) {
        return ResponseEntity.ok().build();
    }

    /**
     * 応募情報を削除する
     *
     * @param userId    ユーザーID
     * @param companyId 企業ID
     * @return ResponseEntity
     */
    @DeleteMapping("/users/{userId}/companies/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "ユーザーの応募情報を削除する")
    @ApiResponses({
            @ApiResponse(responseCode = "204", ref = OpenApiConstant.DELETED_SUCCESS),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "404", ref = OpenApiConstant.NOT_FOUND),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> deletePosted(@PathVariable("userId") @Min(1) long userId,
                                          @PathVariable("companyId") @Min(1) long companyId) {
        return ResponseEntity.noContent().build();
    }
}
