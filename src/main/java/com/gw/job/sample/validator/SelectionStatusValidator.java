package com.gw.job.sample.validator;

import com.gw.job.sample.annotations.SelectionStatusConstraint;
import com.gw.job.sample.entity.enums.SelectionStatus;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SelectionStatusValidator implements ConstraintValidator<SelectionStatusConstraint, SelectionStatus> {

    /**
     * SelectionStatusのバリデーションチェックを行う
     *
     * @param value   設定値
     * @param context context
     * @return trueの場合チェック成功
     */
    @Override
    public boolean isValid(SelectionStatus value, ConstraintValidatorContext context) {
        return SelectionStatus.INVALID != value;
    }
}
