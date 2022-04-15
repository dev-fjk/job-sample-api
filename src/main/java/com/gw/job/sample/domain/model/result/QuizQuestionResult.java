package com.gw.job.sample.domain.model.result;

import com.gw.job.sample.infrastructure.model.entity.Answer;
import com.gw.job.sample.infrastructure.model.entity.Quiz;
import java.util.List;
import lombok.Builder;
import lombok.Value;

/**
 * クイズの問題取得結果
 */
@Value
@Builder
public class QuizQuestionResult {

    List<Quiz> quizList;

    List<Answer> answerList;

    /**
     * 取得したクイズ件数を取得する
     *
     * @return 取得件数
     */
    public int getCount() {
        return quizList.size();
    }
}
