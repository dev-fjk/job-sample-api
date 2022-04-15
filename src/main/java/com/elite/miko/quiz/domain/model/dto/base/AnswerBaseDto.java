package com.elite.miko.quiz.domain.model.dto.base;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public abstract class AnswerBaseDto {

    private String answer1;

    private String answer2;

    private String answer3;

    private String answer4;

    private Integer correctNumber;
}
