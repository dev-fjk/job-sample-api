package com.gw.job.sample.annotations;

import com.gw.job.sample.entity.enums.SelectionStatus;
import com.gw.job.sample.validator.SelectionStatusValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 選考状況のバリデーションを行う独自アノテーション定義IF
 */
@Documented
@Constraint(validatedBy = SelectionStatusValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SelectionStatusConstraint {

    String message() default "無効な値です";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
