package com.elite.miko.quiz.domain.service;

import com.elite.miko.quiz.domain.model.dto.QuizAddDto;
import com.elite.miko.quiz.domain.model.result.QuizQuestionResult;

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
