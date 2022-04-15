package com.gw.job.sample.presentation.controller;

import com.gw.job.sample.config.OpenApiConstant;
import com.gw.job.sample.domain.model.dto.QuizAddDto;
import com.gw.job.sample.domain.model.dto.QuizUpdateListDto;
import com.gw.job.sample.domain.service.QuizAdminService;
import com.gw.job.sample.presentation.converter.ResponseConverter;
import com.gw.job.sample.presentation.model.form.QuizAddForm;
import com.gw.job.sample.presentation.model.form.QuizUpdateForm;
import com.gw.job.sample.presentation.model.response.QuizManageListResponse;
import com.gw.job.sample.presentation.validator.QuizValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(path = QuizAdminController.BASE_PATH)
@RequiredArgsConstructor
@Tag(name = QuizAdminController.BASE_PATH, description = "クイズ管理用のAPI JWTでの認証が必要")
public class QuizAdminController {

    public static final String BASE_PATH = "/miko/v1/admin/";

    private final QuizAdminService adminService;

    private final ResponseConverter responseConverter;
    private final QuizValidator quizValidator;
    private final ModelMapper modelMapper;

    /**
     * クイズ一覧の取得を行う
     *
     * @param start : 取得開始位置
     * @param count : 取得件数
     * @return 取得結果
     */
    @GetMapping(path = "/quizzes")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "クイズの一覧を取得する(管理用)")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "クイズ一覧(管理用)", content = @Content(
                    schema = @Schema(implementation = QuizManageListResponse.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE
            )),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "401", ref = OpenApiConstant.UNAUTHORIZED),
            @ApiResponse(responseCode = "403", ref = OpenApiConstant.FORBIDDEN),
            @ApiResponse(responseCode = "404", ref = OpenApiConstant.QUIZ_NOT_FOUND),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> fetchQuiz(
            @RequestParam(name = "start", required = false, defaultValue = "1") @Min(1) int start,
            @RequestParam(name = "count", required = false, defaultValue = "20") @Range(min = 1, max = 50) int count) {
        var quizManageResult = adminService.fetchQuiz(start - 1, count);
        return new ResponseEntity<>(responseConverter.convert(quizManageResult), HttpStatus.OK);
    }

    /**
     * リクエスト中のクイズ一覧の取得を行う
     *
     * @param start : 取得開始位置
     * @param count : 取得件数
     * @return 取得結果
     */
    @GetMapping(path = "/quizzes/requests")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "リクエスト中のクイズ一覧を取得する")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "リクエスト中のクイズ一覧", content = @Content(
                    schema = @Schema(implementation = QuizManageListResponse.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE
            )),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "401", ref = OpenApiConstant.UNAUTHORIZED),
            @ApiResponse(responseCode = "403", ref = OpenApiConstant.FORBIDDEN),
            @ApiResponse(responseCode = "404", ref = OpenApiConstant.REQUEST_QUIZ_NOT_FOUND),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> fetchQuizRequest(
            @RequestParam(name = "start", required = false, defaultValue = "1") @Min(1) int start,
            @RequestParam(name = "count", required = false, defaultValue = "20") @Range(min = 1, max = 50) int count) {
        var quizManageResult = adminService.fetchRequestQuiz(start - 1, count);
        return new ResponseEntity<>(responseConverter.convert(quizManageResult), HttpStatus.OK);
    }

    /**
     * クイズの追加を行う
     *
     * @param quizAddForm : クイズ追加Form
     * @return 追加成功時は201を返却
     */
    @PostMapping(path = "/quizzes")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "クイズの追加を行う")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = QuizAddForm.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", ref = OpenApiConstant.INSERTED_SUCCESS),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "401", ref = OpenApiConstant.UNAUTHORIZED),
            @ApiResponse(responseCode = "403", ref = OpenApiConstant.FORBIDDEN),
            @ApiResponse(responseCode = "404", ref = OpenApiConstant.QUIZ_NOT_FOUND),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> addQuiz(@Validated @RequestBody QuizAddForm quizAddForm) {
        adminService.insertQuiz(modelMapper.map(quizAddForm, QuizAddDto.class));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * クイズの更新を行う
     *
     * @param quizUpdateForm : クイズ更新用Form
     * @return 更新成功の場合204を返却
     */
    @PutMapping(path = "/quizzes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "クイズの更新を行う")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = QuizUpdateForm.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", ref = OpenApiConstant.UPDATED_SUCCESS),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "401", ref = OpenApiConstant.UNAUTHORIZED),
            @ApiResponse(responseCode = "403", ref = OpenApiConstant.FORBIDDEN),
            @ApiResponse(responseCode = "404", ref = OpenApiConstant.QUIZ_NOT_FOUND),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> updateQuiz(@RequestBody QuizUpdateForm quizUpdateForm) {
        quizValidator.validate(quizUpdateForm);
        adminService.updateQuiz(modelMapper.map(quizUpdateForm, QuizUpdateListDto.class));
        return ResponseEntity.noContent().build();
    }

    /**
     * 指定されたクイズIDのクイズと回答の削除を行う
     *
     * @param quizIdList : 削除対象のクイズIDのリスト　クエリパラメータで設定
     * @return 削除成功時は204を返却
     */
    @DeleteMapping(path = "/quizzes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "クイズの削除を行う")
    @Parameters({
            @Parameter(name = "quizIdList", in = ParameterIn.QUERY,
                    description = "削除対象のクイズIDリストカンマ区切りで設定する 最大50件",
                    schema = @Schema(type = "array", format = "int64")
            )
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", ref = OpenApiConstant.DELETED_SUCCESS),
            @ApiResponse(responseCode = "400", ref = OpenApiConstant.BAD_REQUEST),
            @ApiResponse(responseCode = "401", ref = OpenApiConstant.UNAUTHORIZED),
            @ApiResponse(responseCode = "403", ref = OpenApiConstant.FORBIDDEN),
            @ApiResponse(responseCode = "404", ref = OpenApiConstant.QUIZ_NOT_FOUND),
            @ApiResponse(responseCode = "500", ref = OpenApiConstant.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<?> deleteQuiz(@RequestParam(name = "quizIdList") @NotNull List<Long> quizIdList) {
        quizValidator.validate(quizIdList);
        adminService.deleteQuiz(quizIdList);
        return ResponseEntity.noContent().build();
    }
}
