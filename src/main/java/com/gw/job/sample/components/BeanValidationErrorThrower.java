package com.gw.job.sample.components;

import com.gw.job.sample.exception.ValidationException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * BeanValidationErrorを {@link ValidationException} に変換した上で例外を投げるクラス
 */
@Component
public class BeanValidationErrorThrower {

    /**
     * bindingResultがエラーを保持している場合 {@link ValidationException} に変換する
     *
     * @param bindingResult Springのバリデーションエラー保持クラス
     * @throws ValidationException 独自バリデーションエラー時の例外
     */
    public void throwIfHasErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " は " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new ValidationException(errors);
        }
    }
}
