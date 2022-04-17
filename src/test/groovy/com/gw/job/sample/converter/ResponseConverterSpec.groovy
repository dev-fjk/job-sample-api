package com.gw.job.sample.converter

import com.gw.job.sample.entity.response.EmptyResponse
import com.gw.job.sample.entity.response.ResumeResponse
import com.gw.job.sample.entity.result.ResumeResult
import spock.lang.Specification

class ResponseConverterSpec extends Specification {

    ResponseConverter target

    def setup() {
        target = new ResponseConverter()
    }

    def "正常系_convertResumeResponse"() {
        given: "引数の作成"
        def result = ResumeResult.builder()
                .userId(1)
                .lastName("last")
                .firstName("first")
                .birthDate("2000-04-30")
                .jobDescription("jobDescription")
                .build()

        and: "変換結果作成"
        def expected = ResumeResponse.builder()
                .userId(1)
                .lastName("last")
                .firstName("first")
                .birthDate("2000-04-30")
                .jobDescription("jobDescription")
                .build();

        when:
        def actual = target.convertResumeResponse(result)

        then:
        actual == expected
    }

    def "正常系 empty"() {
        when:
        def actual = target.empty()
        then:
        actual.class == EmptyResponse.class
    }
}
