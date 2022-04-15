package com.elite.miko.quiz.presentation.controller

import com.elite.miko.quiz.application.common.config.TokenConfig
import com.elite.miko.quiz.application.common.utility.WebTokenUtil
import com.elite.miko.quiz.domain.service.AdminAccountService
import com.elite.miko.quiz.presentation.converter.ProblemConverter
import com.elite.miko.quiz.presentation.model.form.LoginForm
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = LoginController.class, includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = [QuizExceptionHandler.class, ProblemConverter.class]
))
@ActiveProfiles("noauth")
@ImportAutoConfiguration
class LoginControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    AdminAccountService accountService = Mock()

    @MockBean
    TokenConfig tokenConfig
    @MockBean
    WebTokenUtil tokenUtil

    def "正常系_isLogin"() {
        given:
        def loginForm = new LoginForm()
        loginForm.setAccountId("accountId")
        loginForm.setPassword("password")

        def objectMapper = new ObjectMapper()
        def jsonRequest = objectMapper.writeValueAsString(loginForm)

        1 * accountService.login("accountId", "password") >> "Bearer dummyToken"

        expect:
        mockMvc.perform(post("/miko/v1/login/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
    }
}
