package com.gw.job.sample.components

import com.gw.job.sample.exception.ValidationException
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import spock.lang.Specification

class BeanValidationErrorThrowerSpec extends Specification {

    BeanValidationErrorThrower target

    def setup() {
        target = new BeanValidationErrorThrower()
    }

    def "throwIfHasErrors エラーなし"() {
        given:
        def bindingResult = Mock(BindingResult)
        bindingResult.hasErrors() >> false
        when:
        target.throwIfHasErrors(bindingResult)
        then:
        noExceptionThrown()
    }

    def "throwIfHasErrors エラーがある場合 エラーが連結されValidationExceptionがthrowされる"() {
        given:
        def bindingResult = Mock(BindingResult)
        bindingResult.hasErrors() >> true
        def fieldError = Mock(FieldError)

        and:
        fieldError.field >> "test"
        fieldError.defaultMessage >> "1 から 10 の間にしてください"
        fieldError.rejectedValue >> "11"

        bindingResult.getFieldErrors() >> [fieldError, fieldError]

        when:
        target.throwIfHasErrors(bindingResult)

        then:
        def exception = thrown(ValidationException)
        exception.getMessage() == "test は 1 から 10 の間にしてください, test は 1 から 10 の間にしてください"
    }
}
