package com.gw.job.sample.controller

import com.gw.job.sample.components.BeanValidationErrorThrower
import com.gw.job.sample.entity.response.EmployeeListResponse
import com.gw.job.sample.entity.response.EmployeeResponse
import com.gw.job.sample.entity.selector.EmployeeListSelector
import com.gw.job.sample.service.EmployeeService
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

@WebMvcTest(controllers = EmployeeController.class, includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = [GlobalExceptionHandler.class, BeanValidationErrorThrower.class]
))
@ActiveProfiles(["test"])
@ImportAutoConfiguration
class EmployeeControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    EmployeeService employeeService = Mock()

    def "findOne"() {
        given:
        def employeeId = 1L
        def response = EmployeeResponse.builder().build()
        1 * employeeService.findOne(employeeId) >> response

        expect:
        mockMvc.perform(get("/employee/v1/get/${employeeId}"))
                .andExpect(status().isOk())
    }

    def "findOne employeeIdが最小値未満の場合400エラー"() {
        given:
        0 * employeeService._

        expect:
        mockMvc.perform(get("/employee/v1/get/0"))
                .andExpect(status().isBadRequest())
    }

    def "findAll"() {
        given:
        def selector = new EmployeeListSelector(
                start: 1,
                count: 20
        )
        def response = EmployeeListResponse.builder().build()

        def uri = UriComponentsBuilder.fromUriString("/employee/v1/get/")
                .queryParam("start", selector.getStart())
                .queryParam("count", selector.getCount())
                .build().toUri()
        1 * employeeService.findAll(selector) >> response

        expect:
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
    }

    def "findAll バリデーションエラーがある場合400エラー"() {
        given:
        def selector = new EmployeeListSelector(
                start: 0, // startでバリデーションエラー
                count: 20
        )

        def uri = UriComponentsBuilder.fromUriString("/employee/v1/get/")
                .queryParam("start", selector.getStart())
                .queryParam("count", selector.getCount())
                .build().toUri()
        0 * employeeService._

        expect:
        mockMvc.perform(get(uri))
                .andExpect(status().isBadRequest())
    }

}
