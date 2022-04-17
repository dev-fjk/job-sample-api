package com.gw.job.sample.service

import com.gw.job.sample.entity.result.ResumeResult
import com.gw.job.sample.repository.interfaces.ResumeRepository
import spock.lang.Specification

class ResumeServiceImplSpec extends Specification {

    ResumeServiceImpl target
    def resumeRepository = Mock(ResumeRepository)

    def setup() {
        target = new ResumeServiceImpl(resumeRepository)
    }

    def "正常系 findUserResume"() {
        given:
        def userId = 1
        def expected = Optional.ofNullable(ResumeResult.builder().build())

        when:
        def actual = target.fetchUserResume(userId)

        then:
        1 * resumeRepository.fetchUserResume(userId) >> expected
        actual == expected
    }
}
