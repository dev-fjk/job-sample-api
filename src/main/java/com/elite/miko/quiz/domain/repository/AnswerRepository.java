package com.elite.miko.quiz.domain.repository;

import com.elite.miko.quiz.domain.model.dto.QuizAddDto.AnswerAddDto;
import com.elite.miko.quiz.domain.model.dto.QuizUpdateListDto.QuizUpdateDto.AnswerUpdateDto;
import com.elite.miko.quiz.infrastructure.model.entity.Answer;
import java.util.List;

public interface AnswerRepository {

    /**
     * クイズIDの一覧と一致する回答一覧を取得する
     *
     * @param quizIdList : クイズIDのリスト
     * @return 回答一覧
     */
    List<Answer> fetchByQuizIdList(List<Long> quizIdList);

    /**
     * 回答の追加を行う
     *
     * @param quizId       : クイズID
     * @param answerAddDto : 回答追加Dto
     * @return 更新成功の場合trueを返却
     */
    boolean insertAnswer(long quizId, AnswerAddDto answerAddDto);

    /**
     * 回答の更新を行う
     *
     * @param quizId          : クイズID
     * @param answerUpdateDto : 回答更新Dto
     * @return 1件更新時はtrue
     */
    boolean updateAnswer(long quizId, AnswerUpdateDto answerUpdateDto);

    /**
     * 回答の削除を行う
     *
     * @param quizIdList : 削除対象のクイズのクイズIDリスト
     */
    void deleteByQuizIdList(List<Long> quizIdList);
}
