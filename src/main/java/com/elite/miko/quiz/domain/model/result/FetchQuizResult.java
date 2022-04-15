package com.elite.miko.quiz.domain.model.result;

import com.elite.miko.quiz.infrastructure.model.entity.Quiz;
import java.util.List;
import lombok.Builder;
import lombok.Value;

/**
 * クイズテーブル向けの取得結果を保持するResultクラス
 */
@Value
@Builder
public class FetchQuizResult {

    /**
     * 全件数
     */
    long total;

    List<Quiz> quizList;

    /**
     * 取得した件数を取得する
     *
     * @return 取得件数
     */
    public int getCount() {
        return quizList.size();
    }
}
