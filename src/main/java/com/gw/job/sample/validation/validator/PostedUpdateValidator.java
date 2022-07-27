package com.gw.job.sample.validation.validator;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.gw.job.sample.entity.enums.PostedStatus;
import com.gw.job.sample.entity.request.PostedUpdateRequest;
import com.gw.job.sample.validation.constraint.PostedUpdateConstraint;
import com.gw.job.sample.validation.group.PostedUpdateToAcceptedGroup;
import com.gw.job.sample.validation.group.PostedUpdateToOtherStatusGroup;

/**
 * 応募更新処理用バリデーター
 */
public class PostedUpdateValidator implements ConstraintValidator<PostedUpdateConstraint, PostedUpdateRequest> {
    private Validator validator;

    public PostedUpdateValidator(Validator validator) {
        this.validator = validator;
    }

    /**
     * バリデーション条件記載メソッド
     * @param value 応募情報更新リクエスト
     * @param context バリデーションコンテキスト
     * @return {@link boolean} 制約違反しているかの判定結果
     */
    @Override
    public boolean isValid(PostedUpdateRequest value, ConstraintValidatorContext context) {
        // Acceptedステータスに変更するリクエストかそれ以外か判定する
        boolean isUpdateToAcceptedRequest = value.getStatus().equals(PostedStatus.ADOPTED.getValue());
        // リクエストをもとにバリデーショングループを選択する
        Class<?> targetGroup = isUpdateToAcceptedRequest? PostedUpdateToAcceptedGroup.class: PostedUpdateToOtherStatusGroup.class;
        final Set<ConstraintViolation<Object>> violations = validator.validate(value, targetGroup);
        
        if(violations.isEmpty()) return true;
        
        // 制約違反オブジェクトをカスタマイズする
        context.disableDefaultConstraintViolation();
        violations
            .stream()
            .forEach((violation) -> {
                context.buildConstraintViolationWithTemplate(violation.getMessageTemplate())
                    .addPropertyNode(violation.getPropertyPath().toString())
                    .addConstraintViolation();
            });
        
        return false;
    }
}
