package com.elite.miko.quiz.presentation.validator

import com.elite.miko.quiz.UnitTestBase
import com.elite.miko.quiz.application.exception.ValidationException
import com.elite.miko.quiz.presentation.model.form.QuizUpdateForm
import com.fasterxml.jackson.core.type.TypeReference
import spock.lang.Shared
import spock.lang.Unroll

import javax.validation.ConstraintViolationException

class QuizValidatorSpec extends UnitTestBase {

    QuizValidator target

    @Shared
    QuizUpdateForm updateForm

    def setup() {
        // 単項目エラーにならないupdateFormをjsonから生成
        updateForm = readJsonToObject("/form/", "quizUpdateForm",
                new TypeReference<QuizUpdateForm>() {})
        target = new QuizValidator()
    }

    def "正常系_validate(QuizUpdateForm)"() {
        when:
        target.validate(updateForm)
        then:
        noExceptionThrown()
    }

    def "異常系_validate(QuizUpdateForm)_アノテーションでのバリデーションエラーが発生"() {
        when:
        updateForm.quizList[0].quizId = null
        target.validate(updateForm)

        then:
        def exception = thrown(ConstraintViolationException)
        exception.getConstraintViolations().size() == 1
    }

    def "正常系_validate(QuizUpdateForm)_クイズが50件設定"() {
        given:
        def quizList = (1..50).collect() {
            return updateForm.getQuizList().get(0)
        }
        updateForm.setQuizList(quizList)

        when:
        target.validate(updateForm)

        then:
        noExceptionThrown()
    }

    def "異常系_validate(QuizUpdateForm)_クイズが51件設定時は自作バリデーションエラーとなる"() {
        given:
        def quizList = (1..51).collect() {
            return updateForm.getQuizList().get(0)
        }
        updateForm.setQuizList(quizList)

        when:
        target.validate(updateForm)

        then:
        def exception = thrown(ValidationException)
        exception.getMessage() == "クイズIDは最大50件です"
    }

    def "異常系_validate(QuizUpdateForm)_クイズ一覧にnullが含まれる"() {
        given:
        updateForm.getQuizList().add(null)

        when:
        target.validate(updateForm)

        then:
        def exception = thrown(ValidationException)
        exception.getMessage() == "クイズ一覧にnullが含まれています"
    }

    def "異常系_validate(QuizUpdateForm)_エラーの連結確認"() {
        given:
        def quizList = (1..50).collect() {
            return updateForm.getQuizList().get(0)
        }
        quizList.add(null)

        updateForm.setQuizList(quizList)

        when:
        target.validate(updateForm)

        then:
        def exception = thrown(ValidationException)
        exception.getMessage() == "クイズIDは最大50件です,クイズ一覧にnullが含まれています"
    }

    def "正常系_validate(quizIdList)"() {
        given:
        def quizIdList = [1L, 2L, 3L]

        when:
        target.validate(quizIdList)

        then:
        noExceptionThrown()
    }

    @Unroll
    def "異常系_validate(quizIdList)_引数が未設定_#testName"() {
        when:
        target.validate(arg)

        then:
        def exception = thrown(ValidationException)
        exception.getMessage() == "quizIdが設定されていません"

        where:
        testName | arg
        "null"   | null
        "空"      | []
    }

    def "正常系_validate(quizIdList)_50件設定"() {
        given:
        def quizIdList = (1L..50L).collect() {
            return it
        }

        when:
        target.validate(quizIdList)

        then:
        noExceptionThrown()
    }

    def "異常系_validate(quizIdList)_51件設定"() {
        given:
        def quizIdList = (1L..51L).collect() {
            return it
        }

        when:
        target.validate(quizIdList)

        then:
        def exception = thrown(ValidationException)
        exception.getMessage() == "クイズIDは最大50件です"
    }

    def "異常系_validate(quizIdList)_nullが混在"() {
        when:
        target.validate([1L, 2L, null])

        then:
        def exception = thrown(ValidationException)
        exception.getMessage() == "ID一覧にnullが含まれています"
    }

    def "異常系_validate(quizIdList)_エラー連結確認"() {
        given:
        List<Long> quizIdList = (1L..50L).collect() {
            return it
        }
        quizIdList.add(null)

        when:
        target.validate(quizIdList)

        then:
        def exception = thrown(ValidationException)
        exception.getMessage() == "クイズIDは最大50件です,ID一覧にnullが含まれています"
    }
}
