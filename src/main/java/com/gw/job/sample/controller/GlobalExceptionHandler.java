package com.gw.job.sample.controller;

import com.gw.job.sample.entity.response.ProblemResponse;
import com.gw.job.sample.exception.RepositoryControlException;
import com.gw.job.sample.exception.ResourceAlreadyExistException;
import com.gw.job.sample.exception.ResourceNotFoundException;
import com.gw.job.sample.exception.ValidationException;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception発生時にエラーレスポンスに変換するHandler
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    /**
     * リクエストオブジェクトのバリデーションエラー
     *
     * @param exception {@link BindException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemResponse> handleBindException(BindException exception) {
        var errors = exception.getFieldErrors().stream()
                .map(error -> error.getField() + " は " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        var body = ProblemResponse.builder()
                .title("リクエストされたパラメータは正しくありません")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(errors)
                .build();
        return errorResponse(body);
    }

    /**
     * リクエストオブジェクトのバリデーションエラー
     *
     * @param exception {@link ConstraintViolationException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        var errors = exception.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + " は " + v.getMessage())
                .collect(Collectors.joining(", "));

        var body = ProblemResponse.builder()
                .title("リクエストされたパラメータは正しくありません")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(errors)
                .build();
        return errorResponse(body);
    }

    /**
     * リクエストオブジェクトの独自バリデーションエラー
     *
     * @param exception {@link ValidationException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ProblemResponse> handleValidationException(ValidationException exception) {
        var body = ProblemResponse.builder()
                .title("リクエストされたパラメータは正しくありません")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(exception.getMessage())
                .build();
        return errorResponse(body);
    }

    /**
     * リソースが見つからない場合のエラー
     *
     * @param exception {@link ResourceNotFoundException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        var body = ProblemResponse.builder()
                .title("リクエストされたリソースは見つかりませんでした")
                .status(HttpStatus.NOT_FOUND.value())
                .detail(exception.getMessage())
                .build();
        return errorResponse(body);
    }

    /**
     * リソースが既に存在する場合のエラー
     *
     * @param exception {@link ResourceAlreadyExistException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ProblemResponse> handleResourceAlreadyExistException(ResourceAlreadyExistException exception) {
        var body = ProblemResponse.builder()
                .title("リソースが既に存在しています")
                .status(HttpStatus.CONFLICT.value())
                .detail(exception.getMessage())
                .build();
        return errorResponse(body);
    }

    /**
     * インフラ層でのデータ更新時のサーバーエラー
     *
     * @param exception {@link RepositoryControlException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(RepositoryControlException.class)
    public ResponseEntity<ProblemResponse> handleRepositoryControlException(RepositoryControlException exception) {
        var body = ProblemResponse.builder()
                .title("データの更新に失敗しました")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(exception.getMessage())
                .build();
        return errorResponse(body);
    }

    /**
     * その他エラー
     *
     * @param exception {@link RuntimeException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ProblemResponse> handleRuntimeException(RuntimeException exception) {
        var body = ProblemResponse.builder()
                .title("予期しないエラーが発生しました")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(exception.getMessage())
                .build();
        return errorResponse(body);
    }

    /**
     * エラーレスポンスを作成する
     *
     * @param body response body
     * @return {@link ResponseEntity} エラーレスポンス用 Response
     */
    private ResponseEntity<ProblemResponse> errorResponse(ProblemResponse body) {
        return ResponseEntity.status(body.getStatus())
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(body);
    }
}
