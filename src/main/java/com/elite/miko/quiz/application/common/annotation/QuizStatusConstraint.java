package com.elite.miko.quiz.application.common.annotation;

import com.elite.miko.quiz.presentation.validator.QuizStatusValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * クイズステータスチェック用の自作バリデーション
 */
@Documented
@Constraint(validatedBy = QuizStatusValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface QuizStatusConstraint {

    String message() default "無効な値です";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
