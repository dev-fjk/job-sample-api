package com.elite.miko.quiz.domain.model.result;

import com.elite.miko.quiz.infrastructure.model.entity.Answer;
import com.elite.miko.quiz.infrastructure.model.entity.Quiz;
import java.util.List;
import lombok.Builder;
import lombok.Value;

/**
 * 管理用のクイズ取得結果
 */
@Value
@Builder
public class QuizManageResult {

    /**
     * 全件数
     */
    long total;

    /**
     * 取得開始位置
     */
    int start;

    List<Quiz> quizList;

    List<Answer> answerList;

    /**
     * 取得した件数を取得する
     *
     * @return 取得件数
     */
    public int getCount() {
        return quizList.size();
    }
}
