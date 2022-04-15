package com.elite.miko.quiz.domain.model.dto;

import com.elite.miko.quiz.domain.model.dto.base.AnswerBaseDto;
import com.elite.miko.quiz.domain.model.dto.base.QuizDtoBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class QuizAddDto extends QuizDtoBase {

    private AnswerAddDto answer;

    @Data
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = false)
    public static class AnswerAddDto extends AnswerBaseDto {
    }
}
