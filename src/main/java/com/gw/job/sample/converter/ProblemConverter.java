package com.gw.job.sample.converter;

import com.gw.job.sample.entity.response.ProblemResponse;
import com.gw.job.sample.exception.RepositoryControlException;
import com.gw.job.sample.exception.ResourceNotFoundException;
import com.gw.job.sample.exception.ValidationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

/**
 * エラーレスポンスの変換を行う
 */
@Component
public class ProblemConverter {

    /**
     * リクエスト時のValidationエラーを返却する
     *
     * @param exception {@link BindException}
     * @return {@link ProblemResponse}
     */
    public ProblemResponse convert(BindException exception) {
        return ProblemResponse.builder()
                .title("リクエストされたパラメータは正しくありません")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(detail(exception.getFieldErrors()))
                .build();
    }

    /**
     * リクエスト時のValidationエラーを返却する
     *
     * @param exception {@link ConstraintViolationException}
     * @return {@link ProblemResponse}
     */
    public ProblemResponse convert(ConstraintViolationException exception) {
        return ProblemResponse.builder()
                .title("リクエストされたパラメータは正しくありません")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(detail(exception.getConstraintViolations()))
                .build();
    }

    /**
     * リクエスト時のValidationエラーを返却する
     *
     * @param exception {@link ValidationException}
     * @return {@link ProblemResponse}
     */
    public ProblemResponse convert(ValidationException exception) {
        return ProblemResponse.builder()
                .title("リクエストされたパラメータは正しくありません")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(exception.getMessage())
                .build();
    }


    /**
     * 404エラーを返す
     *
     * @param exception {@link ResourceNotFoundException}
     * @return {@link ProblemResponse}
     */
    public ProblemResponse convert(ResourceNotFoundException exception) {
        return ProblemResponse.builder()
                .title("リクエストされたリソースは見つかりませんでした")
                .status(HttpStatus.NOT_FOUND.value())
                .detail(exception.getMessage())
                .build();
    }

    /**
     * データ更新時のエラーを返す
     *
     * @param exception {@link RepositoryControlException}
     * @return {@link ProblemResponse}
     */
    public ProblemResponse convert(RepositoryControlException exception) {
        return ProblemResponse.builder()
                .title("データの更新で失敗しました")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(exception.getMessage())
                .build();
    }

    /**
     * catchされなかったエラーを返す
     *
     * @param exception {@link RuntimeException}
     * @return {@link ProblemResponse}
     */
    public ProblemResponse convert(RuntimeException exception) {
        return ProblemResponse.builder()
                .title("予期しないエラーが発生しました")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(exception.getMessage())
                .build();
    }

    /**
     * BindingResult使用時のバリデーションエラーの詳細を作成して変九役する
     *
     * @param errors : FieldErrors
     * @return エラー詳細
     */
    private String detail(List<FieldError> errors) {
        return errors.stream()
                .map(error -> error.getField() + " は " + error.getDefaultMessage() + ": " + error.getRejectedValue())
                .collect(Collectors.joining(", "));
    }

    /**
     * Validator使用時のバリデーションエラーの詳細を作成して変九役する
     *
     * @param violations : ConstraintViolationのセット
     * @return エラー詳細
     */
    private String detail(Set<ConstraintViolation<?>> violations) {
        return violations.stream()
                .map(v -> v.getPropertyPath() + " は " + v.getMessage() + ": " + v.getInvalidValue())
                .collect(Collectors.joining(", "));
    }
}
