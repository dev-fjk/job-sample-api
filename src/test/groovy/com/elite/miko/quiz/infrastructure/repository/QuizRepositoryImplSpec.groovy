package com.elite.miko.quiz.infrastructure.repository

import com.elite.miko.quiz.application.exception.RepositoryControlException
import com.elite.miko.quiz.application.exception.ResourceNotFoundException
import com.elite.miko.quiz.domain.model.consts.QuizStatus
import com.elite.miko.quiz.domain.model.dto.QuizAddDto
import com.elite.miko.quiz.domain.model.dto.QuizUpdateListDto
import com.elite.miko.quiz.domain.repository.QuizRepository
import com.elite.miko.quiz.infrastructure.dao.QuizDao
import com.elite.miko.quiz.infrastructure.model.entity.Quiz
import org.modelmapper.ModelMapper
import spock.lang.Specification
import spock.lang.Unroll

class QuizRepositoryImplSpec extends Specification {

    QuizRepository target

    def quizDao = Mock(QuizDao)
    def modelMapper = Mock(ModelMapper)

    def setup() {
        target = new QuizRepositoryImpl(quizDao, modelMapper)
    }

    def "正常系_fetchRandomQuiz"() {
        given:
        def count = 10
        def quizList = (1..10).collect() {
            return new Quiz()
        }
        when:
        def actual = target.fetchRandomQuiz(count)

        then:
        1 * quizDao.fetchQuizRandom(count, QuizStatus.ENABLED.getValue()) >> quizList
        actual.getCount() == 10
        actual.getQuizList() == quizList
    }

    def "正常系_fetchQuiz_ステータス指定なし"() {
        given:
        def start = 0
        def count = 10
        def quizList = (1..10).collect() {
            return new Quiz()
        }
        when:
        def actual = target.fetchQuiz(start, count)

        then:
        1 * quizDao.fetchByIfPresentStatus(_, null) >> quizList
        actual.getCount() == 10
        actual.getQuizList() == quizList
    }

    def "正常系_fetchQuiz_ステータス指定あり"() {
        given:
        def start = 0
        def count = 10
        def quizList = (1..10).collect() {
            return new Quiz()
        }
        when:
        def actual = target.fetchQuiz(start, count, QuizStatus.REQUEST)

        then:
        1 * quizDao.fetchByIfPresentStatus(_, QuizStatus.REQUEST.getValue()) >> quizList
        actual.getCount() == 10
        actual.getQuizList() == quizList
    }

    def "正常系_fetchByQuizIdSetForUpdate"() {
        given:
        Set<Long> quizIdSet = [1L, 2L, 3L]
        def quizList = (1..10).collect() {
            return new Quiz()
        }
        when:
        def actual = target.fetchByQuizIdSetForUpdate(quizIdSet)

        then:
        1 * quizDao.fetchByQuizIdSetForUpdate(quizIdSet) >> quizList
        actual.getCount() == 10
        actual.getQuizList() == quizList
    }

    def "異常系_fetchByQuizIdSetForUpdate_引数がnull"() {
        when:
        target.fetchByQuizIdSetForUpdate(null)
        then:
        thrown(NullPointerException)
        0 * quizDao.fetchByQuizIdSetForUpdate(_)
    }

    def "正常系_insertQuiz"() {
        given:
        def quizAddDto = new QuizAddDto()
        def insertQuiz = new Quiz()

        1 * modelMapper.map(quizAddDto, Quiz.class) >> insertQuiz
        1 * quizDao.insert(insertQuiz) >> 1

        when:
        def actual = target.insertQuiz(quizAddDto)

        then:
        actual == insertQuiz
    }

    def "異常系_insertQuiz_更新件数が0件の場合は例外が発生"() {
        given:
        def quizAddDto = new QuizAddDto()
        def insertQuiz = new Quiz()

        1 * modelMapper.map(quizAddDto, Quiz.class) >> insertQuiz
        1 * quizDao.insert(insertQuiz) >> 0

        when:
        target.insertQuiz(quizAddDto)

        then:
        def exception = thrown(RepositoryControlException)
        exception.getMessage() == "クイズの登録に失敗しました"
    }

    @Unroll
    def "正常系_updateQuiz_#testName"() {
        given:
        def updateDto = new QuizUpdateListDto.QuizUpdateDto()
        def updateQuiz = new Quiz()

        1 * modelMapper.map(updateDto, Quiz.class) >> updateQuiz
        1 * quizDao.update(updateQuiz) >> updateCount

        when:
        def actual = target.updateQuiz(updateDto)

        then:
        actual == expected

        where:
        testName | updateCount || expected
        "1件更新"   | 1           || true
        "0件更新"   | 0           || false
        "2件更新"   | 2           || false
    }

    def "異常系_updateQuiz_引数がnull"() {
        when:
        target.updateQuiz(null)
        then:
        thrown(NullPointerException)
        0 * modelMapper.map(*_)
        0 * quizDao.update(_)
    }

    def "正常系_deleteByQuizIdList"() {
        def quizIdList = [1L, 2L, 3L]

        when:
        target.deleteByQuizIdList(quizIdList)

        then:
        1 * quizDao.deleteByQuizIdList(quizIdList) >> 3
        noExceptionThrown()
    }

    def "異常系_deleteByQuizIdList_削除件数が0件"() {
        def quizIdList = [1L, 2L, 3L]

        when:
        target.deleteByQuizIdList(quizIdList)

        then:
        1 * quizDao.deleteByQuizIdList(quizIdList) >> 0
        def actual = thrown(ResourceNotFoundException)
        actual.getMessage() == "削除対象のクイズが見つかりません"
    }

    def "異常系_deleteByQuizIdList_引数がnull"() {
        when:
        target.deleteByQuizIdList(null)
        then:
        thrown(NullPointerException)
        0 * quizDao.deleteByQuizIdList(_)
    }
}
