package com.gw.job.sample.controller;

import com.gw.job.sample.exception.RepositoryControlException;
import com.gw.job.sample.exception.ResourceNotFoundException;
import com.gw.job.sample.exception.ValidationException;
import com.gw.job.sample.converter.ProblemConverter;
import com.gw.job.sample.entity.response.ProblemResponse;
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

    private final ProblemConverter problemConverter;

    /**
     * リクエストオブジェクトのバリデーションエラー
     *
     * @param exception {@link BindException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemResponse> handleBindException(BindException exception) {
        return this.errorResponses(HttpStatus.BAD_REQUEST, problemConverter.convert(exception));
    }

    /**
     * リクエストオブジェクトのバリデーションエラー
     *
     * @param exception {@link ConstraintViolationException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        return this.errorResponses(HttpStatus.BAD_REQUEST, problemConverter.convert(exception));
    }

    /**
     * リクエストオブジェクトの独自バリデーションエラー
     *
     * @param exception {@link ValidationException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ProblemResponse> handleValidationException(ValidationException exception) {
        return this.errorResponses(HttpStatus.BAD_REQUEST, problemConverter.convert(exception));
    }

    /**
     * リソースが見つからない場合のエラー
     *
     * @param exception {@link ResourceNotFoundException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return this.errorResponses(HttpStatus.NOT_FOUND, problemConverter.convert(exception));
    }

    /**
     * インフラ層でのデータ更新時の例外
     *
     * @param exception {@link RepositoryControlException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(RepositoryControlException.class)
    public ResponseEntity<ProblemResponse> handleRepositoryControlException(RepositoryControlException exception) {
        return this.errorResponses(HttpStatus.INTERNAL_SERVER_ERROR, problemConverter.convert(exception));
    }

    /**
     * その他エラー
     *
     * @param exception {@link RuntimeException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ProblemResponse> handleRuntimeException(RuntimeException exception) {
        exception.printStackTrace();
        return this.errorResponses(HttpStatus.INTERNAL_SERVER_ERROR, problemConverter.convert(exception));
    }

    /**
     * エラーレスポンスを作成する
     *
     * @param status : HTTPステータス
     * @param body   : HTTPボディ
     * @return エラーレスポンス
     */
    private ResponseEntity<ProblemResponse> errorResponses(HttpStatus status, ProblemResponse body) {
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(body);
    }
}
