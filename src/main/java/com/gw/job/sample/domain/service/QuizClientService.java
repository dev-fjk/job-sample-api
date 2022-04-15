package com.gw.job.sample.domain.service;

import com.gw.job.sample.domain.model.dto.QuizAddDto;
import com.gw.job.sample.domain.model.result.QuizQuestionResult;

public interface QuizClientService {

    /**
     * クライアント向けのクイズ問題の取得を行う
     *
     * @param count : 取得指定件数
     * @return クイズ取得結果
     */
    QuizQuestionResult fetchQuiz(int count);

    /**
     * クイズの追加リクエストを行う
     *
     * @param quizAddDto : クイズ追加DTO
     */
    void quizRequest(QuizAddDto quizAddDto);
}
