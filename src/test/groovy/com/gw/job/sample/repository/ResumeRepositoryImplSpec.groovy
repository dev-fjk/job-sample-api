package com.gw.job.sample.repository

import com.gw.job.sample.dao.ResumeDao
import com.gw.job.sample.entity.Resume
import com.gw.job.sample.entity.result.ResumeResult
import spock.lang.Specification

import java.time.LocalDate

class ResumeRepositoryImplSpec extends Specification {

    ResumeRepository target
    def resumeDao = Mock(ResumeDao)

    def setup() {
        target = new ResumeRepository(resumeDao)
    }

    def "正常系 fetchUserResume レジュメ取得成功"() {
        given:
        def userId = 1

        and:
        def resume = new Resume()
        resume.setBirthDate(LocalDate.of(2022, 4, 30))
        def result = ResumeResult.of(resume)

        1 * resumeDao.findByUserId(userId) >> resume

        when:
        def actual = target.findOne(userId)

        then:
        actual == Optional.ofNullable(result)
    }

    def "正常系 fetchUserResume レジュメが存在しない場合空Optionalが返却"() {
        given:
        def userId = 1

        when:
        def actual = target.findOne(userId)

        then:
        1 * resumeDao.findByUserId(userId) >> null
        actual == Optional.empty()
    }
}
