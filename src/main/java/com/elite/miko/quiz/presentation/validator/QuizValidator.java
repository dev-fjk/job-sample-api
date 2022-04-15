package com.elite.miko.quiz.presentation.validator;

import com.elite.miko.quiz.application.exception.ValidationException;
import com.elite.miko.quiz.presentation.model.form.QuizUpdateForm;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

@Component
public class QuizValidator {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * クイズ更新リクエストへのバリデーションチェックを行う
     *
     * @param updateForm : クイズ更新リクエスト
     * @throws ConstraintViolationException : アノテーションでの単項目エラー時にthrow
     * @throws ValidationException          : 独自バリデーションエラー時例外
     */
    public void validate(QuizUpdateForm updateForm) throws ConstraintViolationException, ValidationException {

        var constraintViolationSet = validator.validate(updateForm);
        if (CollectionUtils.isNotEmpty(constraintViolationSet)) {
            throw new ConstraintViolationException(constraintViolationSet);
        }

        final List<String> validationErrors = new ArrayList<>();
        if (updateForm.getQuizList().size() > 50) {
            validationErrors.add("クイズIDは最大50件です");
        }
        if (updateForm.getQuizList().contains(null)) {
            validationErrors.add("クイズ一覧にnullが含まれています");
        }
        if (CollectionUtils.isNotEmpty(validationErrors)) {
            throw new ValidationException(validationErrors);
        }
    }

    /**
     * クイズID一覧の入力チェック用の自作バリデータ
     * 異常値の場合はValidationExceptionをThrowする
     *
     * @param quizIdList : クイズID一覧
     * @throws ValidationException バリデーションエラー時例外
     */
    public void validate(List<Long> quizIdList) throws ValidationException {

        if (CollectionUtils.isEmpty(quizIdList)) {
            throw new ValidationException("quizIdが設定されていません");
        }

        final List<String> validationErrors = new ArrayList<>();
        if (quizIdList.size() > 50) {
            validationErrors.add("クイズIDは最大50件です");
        }
        if (quizIdList.contains(null)) {
            validationErrors.add("ID一覧にnullが含まれています");
        }
        if (CollectionUtils.isNotEmpty(validationErrors)) {
            throw new ValidationException(validationErrors);
        }
    }
}
