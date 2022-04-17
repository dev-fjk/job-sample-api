package com.gw.job.sample.controller

import com.gw.job.sample.converter.ProblemConverter
import com.gw.job.sample.converter.ResponseConverter
import com.gw.job.sample.entity.result.ResumeResult
import com.gw.job.sample.service.interfaces.ResumeService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = ResumeController.class, includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = [GlobalExceptionHandler.class, ProblemConverter.class]
))
@ActiveProfiles(["test"])
@ImportAutoConfiguration
class ResumeControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    ResumeService resumeService = Mock()

    @SpringBean
    ResponseConverter responseConverter = Mock()

    def "正常系 getResume レジュメ取得成功"() {
        given:
        def userId = 1
        def result = ResumeResult.builder().build()
        def resultOpt = Optional.ofNullable(result)

        and:
        def requestUri = UriComponentsBuilder.fromUriString("/resume/v1/users/{userId}")
                .buildAndExpand(Map.of("userId", userId)).toUri()

        1 * resumeService.fetchUserResume(userId) >> resultOpt
        1 * responseConverter.convertResumeResponse(result)

        expect:
        mockMvc.perform(get(requestUri)).andExpect(status().isOk())
    }

    def "正常系 getResume レジュメが存在しない場合空body作成のConverterが起動"() {
        given:
        def userId = 1
        def emptyOpt = Optional.empty()

        and:
        def requestUri = UriComponentsBuilder.fromUriString("/resume/v1/users/{userId}")
                .buildAndExpand(Map.of("userId", userId)).toUri()

        1 * resumeService.fetchUserResume(userId) >> emptyOpt
        1 * responseConverter.empty()

        expect:
        mockMvc.perform(get(requestUri)).andExpect(status().isOk())
    }

    def "異常系 getResume userIdが不正値の場合400エラーとなる"() {
        given:
        def requestUri = UriComponentsBuilder.fromUriString("/resume/v1/users/{userId}")
                .buildAndExpand(Map.of("userId", 0)).toUri()

        expect:
        mockMvc.perform(get(requestUri)).andExpect(status().isBadRequest())
    }
}
