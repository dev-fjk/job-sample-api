package com.elite.miko.quiz.presentation.controller

import com.elite.miko.quiz.UnitTestBase
import com.elite.miko.quiz.application.common.config.TokenConfig
import com.elite.miko.quiz.application.common.utility.WebTokenUtil
import com.elite.miko.quiz.domain.model.dto.QuizAddDto
import com.elite.miko.quiz.domain.model.result.QuizQuestionResult
import com.elite.miko.quiz.domain.service.QuizClientService
import com.elite.miko.quiz.presentation.converter.ProblemConverter
import com.elite.miko.quiz.presentation.converter.ResponseConverter
import com.elite.miko.quiz.presentation.model.form.QuizAddForm
import com.elite.miko.quiz.presentation.model.response.QuizQuestionListResponse
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.modelmapper.ModelMapper
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
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = QuizClientController.class, includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = [QuizExceptionHandler.class, ProblemConverter.class]
))
@ActiveProfiles(["test", "noauth"])
@ImportAutoConfiguration
class QuizClientControllerSpec extends UnitTestBase {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    QuizClientService clientService = Mock()
    @SpringBean
    ResponseConverter responseConverter = Mock()
    @SpringBean
    ModelMapper modelMapper = Mock()

    @MockBean
    TokenConfig tokenConfig
    @MockBean
    WebTokenUtil tokenUtil

    @Unroll
    def "正常系_fetchQuiz_#testName"() {
        given:
        def count = inputCount
        def result = QuizQuestionResult.builder().build()
        1 * clientService.fetchQuiz(count) >> result
        1 * responseConverter.convert(result) >> new QuizQuestionListResponse()

        expect:
        mockMvc.perform(get("/miko/v1/client/quizzes" + "?count=" + count))
                .andExpect(status().isOk())

        where:
        testName | inputCount
        "10件取得"  | 10
        "100件取得" | 100
    }

    @Unroll
    def "異常系_fetchQuiz_400エラー_#testName"() {
        given:
        def count = inputCount
        def result = QuizQuestionResult.builder().build()
        0 * clientService.fetchQuiz(count) >> result
        0 * responseConverter.convert(result) >> new QuizQuestionListResponse()

        expect:
        mockMvc.perform(get("/miko/v1/client/quizzes" + "?count=" + count))
                .andExpect(status().isBadRequest())

        where:
        testName | inputCount
        "9件取得"   | 9
        "101件取得" | 101
    }

    @Unroll
    def "正常系_quizRequest"() {
        given:
        def quizAddForm = readJsonToObject("/form/", "quizAddForm",
                new TypeReference<QuizAddForm>() {})

        def objectMapper = new ObjectMapper()
        def jsonRequest = objectMapper.writeValueAsString(quizAddForm)

        1 * modelMapper.map(quizAddForm, QuizAddDto.class)
        1 * clientService.quizRequest(_)

        expect:
        mockMvc.perform(post("/miko/v1/client/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
    }

    @Unroll
    def "異常系_quizRequest_バリデーションエラー時は400エラー"() {
        given:
        def quizAddForm = readJsonToObject("/form/", "quizAddForm",
                new TypeReference<QuizAddForm>() {}) as QuizAddForm
        quizAddForm.setQuestion(null)

        def objectMapper = new ObjectMapper()
        def jsonRequest = objectMapper.writeValueAsString(quizAddForm)

        0 * modelMapper.map(quizAddForm, QuizAddDto.class)
        0 * clientService.quizRequest(_)

        expect:
        mockMvc.perform(post("/miko/v1/client/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest())
    }
}
