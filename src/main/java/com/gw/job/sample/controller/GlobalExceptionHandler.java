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
    public ProblemResponse handleBindException(BindException exception) {
        var errors = exception.getFieldErrors().stream()
                .map(error -> error.getField() + " は " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ProblemResponse.builder()
                .title("リクエストされたパラメータは正しくありません")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(errors)
                .build();
    }

    /**
     * リクエストオブジェクトのバリデーションエラー
     *
     * @param exception {@link ConstraintViolationException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemResponse handleConstraintViolationException(ConstraintViolationException exception) {
        var errors = exception.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + " は " + v.getMessage())
                .collect(Collectors.joining(", "));

        return ProblemResponse.builder()
                .title("リクエストされたパラメータは正しくありません")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(errors)
                .build();
    }

    /**
     * リクエストオブジェクトの独自バリデーションエラー
     *
     * @param exception {@link ValidationException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(ValidationException.class)
    public ProblemResponse handleValidationException(ValidationException exception) {
        return ProblemResponse.builder()
                .title("リクエストされたパラメータは正しくありません")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(exception.getMessage())
                .build();
    }

    /**
     * リソースが見つからない場合のエラー
     *
     * @param exception {@link ResourceNotFoundException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemResponse handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ProblemResponse.builder()
                .title("リクエストされたリソースは見つかりませんでした")
                .status(HttpStatus.NOT_FOUND.value())
                .detail(exception.getMessage())
                .build();
    }

    /**
     * リソースが既に存在する場合のエラー
     *
     * @param exception {@link ResourceAlreadyExistException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ProblemResponse handleResourceNotFoundException(ResourceAlreadyExistException exception) {
        return ProblemResponse.builder()
                .title("リソースが既に存在しています")
                .status(HttpStatus.CONFLICT.value())
                .detail(exception.getMessage())
                .build();
    }

    /**
     * インフラ層でのデータ更新時の例外
     *
     * @param exception {@link RepositoryControlException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(RepositoryControlException.class)
    public ProblemResponse handleRepositoryControlException(RepositoryControlException exception) {
        return ProblemResponse.builder()
                .title("データの更新で失敗しました")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(exception.getMessage())
                .build();
    }

    /**
     * その他エラー
     *
     * @param exception {@link RuntimeException}
     * @return エラーレスポンス
     */
    @ExceptionHandler(RuntimeException.class)
    public ProblemResponse handleRuntimeException(RuntimeException exception) {
        return ProblemResponse.builder()
                .title("予期しないエラーが発生しました")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(exception.getMessage())
                .build();
    }
}
