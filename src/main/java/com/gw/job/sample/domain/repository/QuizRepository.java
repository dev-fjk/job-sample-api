package com.gw.job.sample.domain.repository;

import com.gw.job.sample.domain.model.consts.QuizStatus;
import com.gw.job.sample.domain.model.dto.QuizAddDto;
import com.gw.job.sample.domain.model.dto.QuizUpdateListDto.QuizUpdateDto;
import com.gw.job.sample.domain.model.result.FetchQuizResult;
import com.gw.job.sample.infrastructure.model.entity.Quiz;
import java.util.List;
import java.util.Set;
import lombok.NonNull;

public interface QuizRepository {

    /**
     * クイズの問題をランダムで指定件数文取得する
     *
     * @param count : 指定取得件数
     * @return クイズ取得結果
     */
    FetchQuizResult fetchRandomQuiz(int count);

    /**
     * クイズ一覧の取得を行う
     *
     * @param start : offset 取得開始
     * @param count : limit 取得件数
     * @return クイズ取得結果
     */
    FetchQuizResult fetchQuiz(int start, int count);

    /**
     * クイズ一覧の取得を行う
     *
     * @param start  : offset 取得開始
     * @param count  : limit 取得件数
     * @param status : 取得条件に指定するクイズの状態
     * @return クイズ取得結果
     */
    FetchQuizResult fetchQuiz(int start, int count, QuizStatus status);

    /**
     * クイズ一覧を取得し排他ロックを粉う
     *
     * @param quizIdSet : クイズID一覧
     * @return クイズ取得結果
     */
    FetchQuizResult fetchByQuizIdSetForUpdate(@NonNull Set<Long> quizIdSet);

    /**
     * クイズの追加を行う
     *
     * @param quizAddDto : クイズ追加Dto
     * @return 更新成功の場合trueを返却
     */
    Quiz insertQuiz(QuizAddDto quizAddDto);

    /**
     * クイズの更新を行う
     *
     * @param quizUpdateListDto : クイズ更新Dto
     * @return 1件更新時はtrue
     */
    boolean updateQuiz(@NonNull QuizUpdateDto quizUpdateListDto);

    /**
     * クイズの削除を行う
     *
     * @param quizIdList : 削除対象のクイズのクイズIDリスト
     */
    void deleteByQuizIdList(List<Long> quizIdList);
}
