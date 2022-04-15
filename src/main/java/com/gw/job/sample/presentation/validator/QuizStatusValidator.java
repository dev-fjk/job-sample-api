package com.gw.job.sample.presentation.validator;

import com.gw.job.sample.domain.model.consts.QuizStatus;
import com.gw.job.sample.application.common.annotation.QuizStatusConstraint;
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
