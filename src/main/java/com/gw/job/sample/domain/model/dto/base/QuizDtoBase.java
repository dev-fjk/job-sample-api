package com.gw.job.sample.domain.model.dto.base;

import com.gw.job.sample.domain.model.consts.QuizStatus;
import lombok.Data;


@Data
public abstract class QuizDtoBase {

    private String question;

    private String commentary;

    private QuizStatus status;
}
