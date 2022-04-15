package com.elite.miko.quiz.infrastructure.repository

import com.elite.miko.quiz.domain.model.dto.QuizAddDto
import com.elite.miko.quiz.domain.model.dto.QuizUpdateListDto
import com.elite.miko.quiz.domain.repository.AnswerRepository
import com.elite.miko.quiz.infrastructure.dao.AnswerDao
import com.elite.miko.quiz.infrastructure.model.entity.Answer
import org.modelmapper.ModelMapper
import spock.lang.Specification
import spock.lang.Unroll

class AnswerRepositoryImplSpec extends Specification {

    AnswerRepository target
    def answerDao = Mock(AnswerDao)
    def modelMapper = Mock(ModelMapper)

    def setup() {
        target = new AnswerRepositoryImpl(answerDao, modelMapper)
    }

    def "正常系_fetchByQuizIdList"() {
        given:
        def quizIdList = [1L, 2L, 3L]
        def answerList = (1L..3L).collect() {
            return new Answer()
        }

        when:
        def actual = target.fetchByQuizIdList(quizIdList)

        then:
        1 * answerDao.fetchByQuizIdList(quizIdList) >> answerList
        actual == answerList
    }

    def "異常系_fetchByQuizIdList_引数がnull"() {
        when:
        target.fetchByQuizIdList(null)
        then:
        thrown(NullPointerException)
        0 * answerDao.fetchByQuizIdList(_)
    }

    @Unroll
    def "正常系_insertAnswer_#testName"() {
        given:
        def quizId = 10L
        def answerAddDto = new QuizAddDto.AnswerAddDto()
        def insertAnswer = new Answer()

        when:
        def actual = target.insertAnswer(quizId, answerAddDto)

        then:
        1 * modelMapper.map(answerAddDto, Answer.class) >> insertAnswer
        1 * answerDao.insert(insertAnswer) >> insertCount
        actual == expected

        where:
        testName | insertCount || expected
        "1件更新"   | 1           || true
        "0件更新"   | 0           || false
        "2件更新"   | 2           || false
    }

    def "異常系_insertAnswer_引数がnull"() {
        when:
        target.insertAnswer(10L, null)

        then:
        thrown(NullPointerException)
        0 * modelMapper.map(*_)
        0 * answerDao.insert(*_)
    }

    def "正常系_updateAnswer_#testName"() {
        given:
        def quizId = 10L
        def answerUpdateDto = new QuizUpdateListDto.QuizUpdateDto.AnswerUpdateDto()
        def updateAnswer = new Answer()

        when:
        def actual = target.updateAnswer(quizId, answerUpdateDto)

        then:
        1 * modelMapper.map(answerUpdateDto, Answer.class) >> updateAnswer
        1 * answerDao.update(updateAnswer) >> insertCount
        actual == expected

        where:
        testName | insertCount || expected
        "1件更新"   | 1           || true
        "0件更新"   | 0           || false
        "2件更新"   | 2           || false
    }

    def "異常系_updateAnswer_引数がnull"() {
        when:
        target.updateAnswer(10L, null)

        then:
        thrown(NullPointerException)
        0 * modelMapper.map(*_)
        0 * answerDao.update(*_)
    }

    def "正常系_deleteByQuizIdList"() {
        given:
        def quizIdList = [1L, 2L, 3L]
        when:
        target.deleteByQuizIdList(quizIdList)
        then:
        noExceptionThrown()
        1 * answerDao.deleteByQuizIdList(quizIdList)
    }

    def "異常系_deleteByQuizIdList_引数がnull"() {
        when:
        target.deleteByQuizIdList(null)

        then:
        thrown(NullPointerException)
        0 * answerDao.deleteByQuizIdList(*_)
    }
}
