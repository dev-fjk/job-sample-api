package com.elite.miko.quiz.domain.model.dto;

import com.elite.miko.quiz.domain.model.dto.base.AnswerBaseDto;
import com.elite.miko.quiz.domain.model.dto.base.QuizDtoBase;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class QuizUpdateListDto {

    private List<QuizUpdateDto> quizList;

    @Data
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = false)
    public static class QuizUpdateDto extends QuizDtoBase {

        private Long quizId;

        private AnswerUpdateDto answer;

        @Data
        @ToString(callSuper = true)
        @EqualsAndHashCode(callSuper = false)
        public static class AnswerUpdateDto extends AnswerBaseDto {
            private Long quizId;
        }
    }
}
