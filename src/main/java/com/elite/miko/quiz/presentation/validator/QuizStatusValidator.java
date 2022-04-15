package com.elite.miko.quiz.presentation.validator;

import com.elite.miko.quiz.domain.model.consts.QuizStatus;
import com.elite.miko.quiz.application.common.annotation.QuizStatusConstraint;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QuizStatusValidator implements ConstraintValidator<QuizStatusConstraint, QuizStatus> {

    /**
     * QuizStatusのバリデーションチェックを行う
     *
     * @param value   : リクエストで渡された値
     * @param context : {@link ConstraintValidatorContext }
     * @return チェック結果
     */
    @Override
    public boolean isValid(QuizStatus value, ConstraintValidatorContext context) {
        return Objects.nonNull(value) && QuizStatus.INVALID != value;
    }
}
