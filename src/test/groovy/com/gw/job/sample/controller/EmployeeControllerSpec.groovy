package com.gw.job.sample.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.gw.job.sample.components.BeanValidationErrorThrower
import com.gw.job.sample.entity.request.EmployeeAddRequest
import com.gw.job.sample.entity.request.EmployeeUpdateRequest
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
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
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

    @Shared
    ObjectMapper objectMapper

    def setupSpec() {
        // java8 dateパッケージのライブラリのparse設定
        objectMapper = new ObjectMapper()
        objectMapper.registerModule(new JavaTimeModule())
    }

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

    def "add"() {
        given:
        def request = new EmployeeAddRequest(
                lastName: "テスト",
                firstName: "太郎",
                departmentCode: 1,
                entryDate: LocalDate.of(2022, 7, 20),
                createdBy: "test"
        )

        and:
        String body = objectMapper.writeValueAsString(request)
        def uri = UriComponentsBuilder.fromUriString("/employee/v1/register")
                .build().toUri()

        1 * employeeService.add(request) >> 1L

        expect:
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(redirectedUrl("/employee/v1/get/1")) // locationUri
                .andExpect(status().isCreated())
    }

    def "add バリデーションエラー"() {
        given:
        def request = new EmployeeAddRequest(
                lastName: null,
        )
        String body = objectMapper.writeValueAsString(request)
        def uri = UriComponentsBuilder.fromUriString("/employee/v1/register")
                .build().toUri()

        0 * employeeService._

        expect:
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
    }

    def "update"() {
        given:
        def employeeId = 1L
        def request = new EmployeeUpdateRequest(
                lastName: "テスト",
                firstName: "太郎",
                departmentCode: 1,
                updatedBy: "test"
        )

        and:
        String body = objectMapper.writeValueAsString(request)
        def uri = UriComponentsBuilder.fromUriString("/employee/v1/save/{employeeId}")
                .buildAndExpand(Map.of("employeeId", employeeId)).toUri()
        1 * employeeService.update(employeeId, request)

        expect:
        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
    }

    def "update パスパラメータで400エラー"() {
        given:
        def employeeId = 0L
        def request = new EmployeeUpdateRequest(
                lastName: "テスト",
                firstName: "太郎",
                departmentCode: 1,
                updatedBy: "test"
        )

        and:
        String body = objectMapper.writeValueAsString(request)
        def uri = UriComponentsBuilder.fromUriString("/employee/v1/save/{employeeId}")
                .buildAndExpand(Map.of("employeeId", employeeId)).toUri()
        0 * employeeService._

        expect:
        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
    }

    def "update bodyで400エラー"() {
        given:
        def employeeId = 1L
        def request = new EmployeeUpdateRequest(
                lastName: null
        )

        and:
        String body = objectMapper.writeValueAsString(request)
        def uri = UriComponentsBuilder.fromUriString("/employee/v1/save/{employeeId}")
                .buildAndExpand(Map.of("employeeId", employeeId)).toUri()
        0 * employeeService._

        expect:
        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
    }

    def "delete"() {
        given:
        def employeeId = 1L
        1 * employeeService.delete(employeeId)

        expect:
        mockMvc.perform(delete("/employee/v1/delete/${employeeId}"))
                .andExpect(status().isNoContent())
    }

    def "delete employeeIdが最小値未満の場合400エラー"() {
        given:
        0 * employeeService._

        expect:
        mockMvc.perform(delete("/employee/v1/delete/0"))
                .andExpect(status().isBadRequest())
    }
}
