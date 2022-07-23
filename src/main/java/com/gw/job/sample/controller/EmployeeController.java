package com.gw.job.sample.controller;

import com.gw.job.sample.config.OpenApiConstant;
import com.gw.job.sample.entity.request.EmployeeAddRequest;
import com.gw.job.sample.entity.request.EmployeeUpdateRequest;
import com.gw.job.sample.entity.response.EmployeeListResponse;
import com.gw.job.sample.entity.response.EmployeeResponse;
import com.gw.job.sample.entity.selector.EmployeeListSelector;
import com.gw.job.sample.service.EmployeeService;
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
@RequestMapping(path = EmployeeController.BASE_PATH)
@RequiredArgsConstructor
@Tag(name = EmployeeController.BASE_PATH, description = "(サンプル用) 社員情報管理API")
public class EmployeeController {

    public static final String BASE_PATH = "/employee/v1";

    private final EmployeeService employeeService;

    /**
     * 社員情報を取得する
     *
     * @param employeeId 社員Id
     * @return 社員情報
     */
    @GetMapping("/get/{employeeId}")
    @Operation(summary = "社員情報を取得する")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "社員情報取得結果",
                    content = @Content(schema = @Schema(implementation = EmployeeResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "404", ref = OpenApiConstant.NOT_FOUND),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<EmployeeResponse> findOne(@PathVariable("employeeId") @Min(1) long employeeId) {
        var response = employeeService.findOne(employeeId);
        return ResponseEntity.ok(response);
    }

    /**
     * 従業員一覧を取得する
     *
     * @param selector      {@link EmployeeListSelector} クエリパラメータ
     * @param bindingResult バリデーションエラー情報を保持するIF
     * @return {@link EmployeeListResponse} をbodyに設定したレスポンス
     */
    @GetMapping("/get/")
    @Operation(summary = "社員情報一覧を取得する")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "社員一覧取得結果", content = @Content(
                    schema = @Schema(implementation = EmployeeListResponse.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE
            )),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<EmployeeListResponse> findAll(@Validated @ModelAttribute EmployeeListSelector selector,
                                                        BindingResult bindingResult) {
        return ResponseEntity.ok().build();
    }

    /**
     * 社員情報を登録する
     *
     * @param request       社員登録リクエスト
     * @param bindingResult バリデーションエラー情報を保持するIF
     * @return locationヘッダーを設定したResponseEntity
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "社員情報を登録する")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = EmployeeAddRequest.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "社員登録成功 作成したリソースへのURIをlocationヘッダーに設定して返す",
                    headers = @Header(name = "location", description = "作成した社員情報取得用のパス",
                            required = true, schema = @Schema(type = "string", example = "/employee/v1/1"))
            ),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> add(@Validated @RequestBody EmployeeAddRequest request, BindingResult bindingResult) {
        long dummyId = 1L;
        return ResponseEntity.created(UriComponentsBuilder.newInstance().path("/employee/v1/{employeeId}}")
                .buildAndExpand(Map.of("employeeId", dummyId)).toUri()).build();
    }

    /**
     * 社員情報を更新する
     *
     * @param employeeId    社員ID
     * @param request       社員更新リクエスト
     * @param bindingResult バリデーションエラー情報を保持するIF
     * @return 更新した社員情報
     */
    @PutMapping("/save/{employeeId}")
    @Operation(summary = "社員情報を更新する")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = EmployeeUpdateRequest.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "更新した社員情報", content = @Content(
                    schema = @Schema(implementation = EmployeeResponse.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE
            )),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "404", ref = OpenApiConstant.NOT_FOUND),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<EmployeeResponse> put(@PathVariable("employeeId") long employeeId,
                                                @RequestBody EmployeeUpdateRequest request,
                                                BindingResult bindingResult) {
        return ResponseEntity.ok().build();
    }

    /**
     * 社員情報を削除する
     *
     * @param employeeId 社員ID
     * @return ResponseEntity
     */
    @DeleteMapping("/delete/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "社員情報を削除する")
    @ApiResponses({
            @ApiResponse(responseCode = "204", ref = OpenApiConstant.DELETED_SUCCESS),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "404", ref = OpenApiConstant.NOT_FOUND),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> delete(@PathVariable("employeeId") long employeeId) {
        return ResponseEntity.noContent().build();
    }
}
