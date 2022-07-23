package com.gw.job.sample.controller;

import com.gw.job.sample.config.OpenApiConstant;
import com.gw.job.sample.entity.request.ResumeAddRequest;
import com.gw.job.sample.entity.request.ResumeUpdateRequest;
import com.gw.job.sample.entity.response.PostedResumeListResponse;
import com.gw.job.sample.entity.response.ResumeResponse;
import com.gw.job.sample.entity.selector.ResumeListSelector;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * レジュメ情報の操作を行うコントローラー
 */
@Validated
@RestController
@RequestMapping(path = ResumeController.BASE_PATH)
@RequiredArgsConstructor
@Tag(name = ResumeController.BASE_PATH, description = "ユーザーの職務経歴情報管理用API")
public class ResumeController {

    public static final String BASE_PATH = "/resume/v1";

    /**
     * レジュメ情報を取得する
     *
     * @param userId ユーザーId
     * @return {@link ResumeResponse}
     */
    @GetMapping("/users/{userId}")
    @Operation(summary = "レジュメ情報を取得する")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "レジュメ取得結果",
                    content = @Content(schema = @Schema(implementation = ResumeResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "404", ref = OpenApiConstant.NOT_FOUND),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<ResumeResponse> getResume(@PathVariable("userId") @Min(1) long userId) {
        return ResponseEntity.ok().build();
    }

    /**
     * 企業へ応募中のレジュメ一覧を取得する
     *
     * @param companyId 企業ID
     * @param selector  {@link ResumeListSelector} クエリパラメータ
     * @return {@link PostedResumeListResponse}
     */
    @GetMapping("/companies/{companyId}")
    @Operation(summary = "企業へ応募中のレジュメ一覧を取得する")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "レジュメ一覧取得結果", content = @Content(
                    schema = @Schema(implementation = PostedResumeListResponse.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE
            )),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<PostedResumeListResponse> getPostedUserResumeList(
            @PathVariable("companyId") long companyId,
            @Validated @ModelAttribute ResumeListSelector selector,
            BindingResult bindingResult) {
        return ResponseEntity.ok().build();
    }

    /**
     * レジュメを登録する
     *
     * @param request       {@link ResumeAddRequest} レジュメ登録リクエスト
     * @param bindingResult バリデーションエラー情報を保持するIF
     * @return locationHeaderを設定したResponseEntity
     */
    @PostMapping("/users/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "レジュメ情報を登録する")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = ResumeAddRequest.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "レジュメ登録成功 作成したリソースへのURIをlocationヘッダーに設定して返す",
                    headers = @Header(name = "location", description = "作成したレジュメ取得用のパス",
                            required = true, schema = @Schema(type = "string", example = "/resume/v1/users/1"))
            ),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> addResume(@Validated @RequestBody ResumeAddRequest request,
                                       BindingResult bindingResult) {
        long dummyUserId = 1L;
        return ResponseEntity.created(UriComponentsBuilder.newInstance().path("/resume/v1/users/{userId}")
                .buildAndExpand(Map.of("userId", dummyUserId)).toUri()).build();
    }

    /**
     * レジュメを更新する
     *
     * @param userId        ユーザーID
     * @param request       {@link ResumeUpdateRequest} 更新リクエスト
     * @param bindingResult 　バリデーションエラー情報を保持するIF
     * @return {@link ResumeResponse} 更新したレジュメ情報
     */
    @PutMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "レジュメ情報を更新する " +
            "在籍企業と経験職種は ID未設定: 追加, 既存のIDを指定: 更新, 既存のID未設定: 削除となる")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = ResumeUpdateRequest.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", ref = OpenApiConstant.UPDATED_SUCCESS),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "404", ref = OpenApiConstant.NOT_FOUND),
            @ApiResponse(responseCode = "409", ref = OpenApiConstant.CONFLICT),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> updateResume(@PathVariable("userId") long userId,
                                          @Validated @RequestBody ResumeUpdateRequest request,
                                          BindingResult bindingResult) {
        return ResponseEntity.ok().build();
    }

    /**
     * レジュメを削除する
     *
     * @param userId ユーザーID
     * @return ResponseEntity
     */
    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "レジュメ情報を削除する")
    @ApiResponses({
            @ApiResponse(responseCode = "204", ref = OpenApiConstant.DELETED_SUCCESS),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "404", ref = OpenApiConstant.NOT_FOUND),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> deleteResume(@PathVariable("userId") long userId) {
        return ResponseEntity.noContent().build();
    }
}
