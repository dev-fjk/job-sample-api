package com.elite.miko.quiz.application.service

import com.elite.miko.quiz.application.exception.RepositoryControlException
import com.elite.miko.quiz.application.exception.ResourceNotFoundException
import com.elite.miko.quiz.domain.model.consts.QuizStatus
import com.elite.miko.quiz.domain.model.dto.QuizAddDto
import com.elite.miko.quiz.domain.model.dto.QuizUpdateListDto
import com.elite.miko.quiz.domain.model.result.FetchQuizResult
import com.elite.miko.quiz.domain.model.result.QuizManageResult
import com.elite.miko.quiz.domain.repository.AnswerRepository
import com.elite.miko.quiz.domain.repository.QuizRepository
import com.elite.miko.quiz.domain.service.QuizAdminService
import com.elite.miko.quiz.infrastructure.model.entity.Answer
import com.elite.miko.quiz.infrastructure.model.entity.Quiz
import spock.lang.Specification
import spock.lang.Unroll

class QuizAdminServiceImplSpec extends Specification {

    QuizAdminService target

    def quizRepository = Mock(QuizRepository)
    def answerRepository = Mock(AnswerRepository)

    def setup() {
        target = new QuizAdminServiceImpl(quizRepository, answerRepository)
    }

    def "正常系_fetchQuiz"() {
        given:
        def quizList = (1..2).collect() {
            def quiz = new Quiz()
            quiz.setQuizId(it)
            return quiz
        }
        def answerList = (1..2).collect() {
            return new Answer()
        }

        def fetchQuizResult = FetchQuizResult.builder()
                .total(2)
                .quizList(quizList)
                .build()
        def quizManageResult = QuizManageResult.builder()
                .total(2)
                .quizList(quizList)
                .answerList(answerList)
                .build()

        1 * quizRepository.fetchQuiz(0, 10) >> fetchQuizResult
        1 * answerRepository.fetchByQuizIdList(_) >> answerList

        when:
        def actual = target.fetchQuiz(0, 10)

        then:
        actual == quizManageResult
    }

    def "正常系_fetchRequestQuiz"() {
        given:
        def quizList = (1L..2L).collect() {
            def quiz = new Quiz()
            quiz.setQuizId(it)
            return quiz
        }
        def answerList = (1..2).collect() {
            return new Answer()
        }

        def fetchQuizResult = FetchQuizResult.builder()
                .total(2)
                .quizList(quizList)
                .build()
        def quizManageResult = QuizManageResult.builder()
                .total(2)
                .quizList(quizList)
                .answerList(answerList)
                .build()

        1 * quizRepository.fetchQuiz(0, 10, QuizStatus.REQUEST) >> fetchQuizResult
        1 * answerRepository.fetchByQuizIdList(_) >> answerList

        when:
        def actual = target.fetchRequestQuiz(0, 10)

        then:
        actual == quizManageResult
    }

    def "準正常系_クイズの取得結果が空の場合空レスポンスが返却"() {
        given:
        def fetchQuizResult = FetchQuizResult.builder()
                .total(100)
                .quizList(Collections.emptyList())
                .build()

        def quizManageResult = QuizManageResult.builder()
                .total(100)
                .quizList(Collections.emptyList())
                .answerList(Collections.emptyList())
                .build()

        when:
        def actual = target.fetchQuiz(0, 10)

        then:
        1 * quizRepository.fetchQuiz(0, 10) >> fetchQuizResult
        actual == quizManageResult
    }

    def "異常系_commonManageQuizFetch_クイズが存在しない場合リソースが存在しないエラーが発生"() {
        given:
        def fetchQuizResult = FetchQuizResult.builder()
                .total(0)
                .quizList(Collections.emptyList())
                .build()

        1 * quizRepository.fetchQuiz(0, 10) >> fetchQuizResult
        0 * answerRepository.fetchByQuizIdList(_)

        when:
        target.fetchQuiz(0, 10)

        then:
        def exception = thrown(ResourceNotFoundException)
        exception.getMessage() == "クイズが見つかりませんでした"
    }

    def "正常系_insertQuiz_登録に成功"() {
        given:
        def quizAddDto = new QuizAddDto()
        def addAnswer = new QuizAddDto.AnswerAddDto()
        quizAddDto.setAnswer(addAnswer)

        def insertedQuiz = new Quiz()
        insertedQuiz.setQuizId(10L)

        when:
        target.insertQuiz(quizAddDto)

        then:
        1 * quizRepository.insertQuiz(quizAddDto) >> insertedQuiz
        1 * answerRepository.insertAnswer(10L, addAnswer) >> true
    }

    def "異常系_insertQuiz_登録に失敗時はリポジトリ操作エラーが発生"() {
        given:
        def quizAddDto = new QuizAddDto()
        def addAnswer = new QuizAddDto.AnswerAddDto()
        quizAddDto.setAnswer(addAnswer)

        def insertedQuiz = new Quiz()
        insertedQuiz.setQuizId(10L)

        1 * quizRepository.insertQuiz(quizAddDto) >> insertedQuiz
        1 * answerRepository.insertAnswer(10L, addAnswer) >> false

        when:
        target.insertQuiz(quizAddDto)

        then:
        def exception = thrown(RepositoryControlException)
        exception.getMessage() == "回答の登録に失敗しました"
    }

    def "正常系_updateQuiz_リクエストのDTOとDBで取得したクイズのIDが全て一致の場合"() {
        given:
        // クイズID 1~3 の合計3件のクイズ情報を持ったDtoを作成
        def quizUpdateListDto = createQuizUpdateListDto()

        // 更新対象のクイズ取得情報を作成
        def fetchQuizList = (1L..3L).collect() {
            def tmpQuiz = new Quiz()
            tmpQuiz.setQuizId(it)
            return tmpQuiz
        }
        def fetchQuizResult = FetchQuizResult
                .builder()
                .quizList(fetchQuizList)
                .build()

        when:
        target.updateQuiz(quizUpdateListDto)

        then:
        1 * quizRepository.fetchByQuizIdSetForUpdate(_) >> fetchQuizResult
        // 更新処理が各テーブルに3回ずつ行われていること
        3 * quizRepository.updateQuiz(_) >> true
        3 * answerRepository.updateAnswer(*_) >> true
    }

    def "準正常系_updateQuiz_DBに存在しないクイズIDを持つデータは更新されない"() {
        given:
        // クイズID 1~3 の合計3件のクイズ情報を持ったDtoを作成
        def quizUpdateListDto = createQuizUpdateListDto()

        // DBのデータは クイズID 1~2の2件のみ
        def fetchQuizList = (1L..2L).collect() {
            def tmpQuiz = new Quiz()
            tmpQuiz.setQuizId(it)
            return tmpQuiz
        }
        def fetchQuizResult = FetchQuizResult
                .builder()
                .quizList(fetchQuizList)
                .build()

        when:
        target.updateQuiz(quizUpdateListDto)

        then:
        1 * quizRepository.fetchByQuizIdSetForUpdate(_) >> fetchQuizResult
        // クイズID 3のデータが更新されず 2回だけ各テーブルに対する更新処理が行われていること
        2 * quizRepository.updateQuiz(_) >> true
        1 * answerRepository.updateAnswer(1L, _) >> true
        1 * answerRepository.updateAnswer(2L, _) >> true
    }

    def "異常系_updateQuiz_更新対象のクイズが見つからない"() {
        given:
        // クイズID 1~3 の合計3件のクイズ情報を持ったDtoを作成
        def quizUpdateListDto = createQuizUpdateListDto()
        def fetchQuizResult = FetchQuizResult
                .builder()
                .quizList(Collections.emptyList())
                .build()

        1 * quizRepository.fetchByQuizIdSetForUpdate(_) >> fetchQuizResult

        when:
        target.updateQuiz(quizUpdateListDto)

        then:
        def exception = thrown(ResourceNotFoundException)
        exception.getMessage() == "更新対象のクイズが見つかりません"
    }

    @Unroll
    def "異常系_updateQuiz_更新に失敗時はリポジトリ操作エラーが発生_#testName"() {
        given:
        // クイズID 1~3 の合計3件のクイズ情報を持ったDtoを作成
        def quizUpdateListDto = createQuizUpdateListDto()

        // 更新対象のクイズ取得情報を作成
        def fetchQuizList = (1L).collect() {
            def tmpQuiz = new Quiz()
            tmpQuiz.setQuizId(it)
            return tmpQuiz
        }
        def fetchQuizResult = FetchQuizResult
                .builder()
                .quizList(fetchQuizList)
                .build()

        1 * quizRepository.fetchByQuizIdSetForUpdate(_) >> fetchQuizResult
        // キャスト不要だが警告出るのでキャスト
        quizRepository.updateQuiz(_ as QuizUpdateListDto.QuizUpdateDto) >> isUpdateQuiz
        answerRepository.updateAnswer(
                _ as long, _ as QuizUpdateListDto.QuizUpdateDto.AnswerUpdateDto
        ) >> isUpdateAnswer

        when:
        target.updateQuiz(quizUpdateListDto)

        then:
        def exception = thrown(RepositoryControlException)
        exception.getMessage() == "クイズの更新に失敗しました クイズID: 1"

        where:
        testName    | isUpdateQuiz | isUpdateAnswer
        "クイズの更新に失敗" | false        | true
        "回答の更新に失敗"  | true         | false
        "両方更新失敗"    | false        | false
    }

    def "正常系deleteQuiz"() {
        given:
        def quizIdList = [1L, 2L, 3L]
        when:
        target.deleteQuiz(quizIdList)
        then:
        1 * answerRepository.deleteByQuizIdList(quizIdList)
        1 * quizRepository.deleteByQuizIdList(quizIdList)
    }

    def static createQuizUpdateListDto() {
        def quizList = (1L..3L).collect() {
            def answerUpdateDto = new QuizUpdateListDto.QuizUpdateDto.AnswerUpdateDto()
            answerUpdateDto.setQuizId(it)

            def quizUpdateDto = new QuizUpdateListDto.QuizUpdateDto()
            quizUpdateDto.setQuizId(it)
            quizUpdateDto.setAnswer(answerUpdateDto)
            return quizUpdateDto
        }

        QuizUpdateListDto quizUpdateListDto = new QuizUpdateListDto()
        quizUpdateListDto.setQuizList(quizList)
        return quizUpdateListDto
    }
}
