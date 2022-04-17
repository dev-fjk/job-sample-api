package com.gw.job.sample.entity.result

import com.gw.job.sample.entity.Resume
import spock.lang.Specification

import java.time.LocalDate

class ResumeResultSpec extends Specification {

    def "正常系 of 変換確認"() {
        given:
        def resume = new Resume()
        resume.userId = 1
        resume.lastName = "last"
        resume.firstName = "first"
        resume.birthDate = LocalDate.of(2022, 4, 30)
        resume.jobDescription = "description"

        when:
        def actual = ResumeResult.of(resume)

        then:
        actual.userId == 1
        actual.lastName == "last"
        actual.firstName == "first"
        actual.birthDate == "2022-04-30"
        actual.jobDescription == "description"
    }

}
