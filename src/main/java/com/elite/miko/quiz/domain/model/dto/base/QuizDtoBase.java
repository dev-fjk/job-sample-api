package com.elite.miko.quiz.domain.model.dto.base;

import com.elite.miko.quiz.domain.model.consts.QuizStatus;
import lombok.Data;
import lombok.ToString;


@Data
public abstract class QuizDtoBase {

    private String question;

    private String commentary;

    private QuizStatus status;
}
