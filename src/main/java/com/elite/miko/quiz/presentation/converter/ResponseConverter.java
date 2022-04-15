package com.elite.miko.quiz.presentation.converter;

import com.elite.miko.quiz.domain.model.result.QuizManageResult;
import com.elite.miko.quiz.domain.model.result.QuizQuestionResult;
import com.elite.miko.quiz.presentation.model.response.QuizManageListResponse;
import com.elite.miko.quiz.presentation.model.response.QuizQuestionListResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseConverter {

    private final QuizConverter quizConverter;

    /**
     * クイズの問題取得結果用レスポンスを作成する
     *
     * @param result : DBのクイズ取得結果
     * @return {@link QuizQuestionListResponse}
     */
    public QuizQuestionListResponse convert(@NonNull QuizQuestionResult result) {
        return new QuizQuestionListResponse(result.getCount(), quizConverter.convert(result));
    }

    /**
     * クイズ管理用情報一覧を持つレスポンスを作成する
     *
     * @param result : DBのクイズ取得結果
     * @return {@link QuizManageListResponse}
     */
    public QuizManageListResponse convert(@NonNull QuizManageResult result) {
        var start = result.getStart() + 1; // 内部で一度-1しているので元々貰った値に戻す
        var quizAll = quizConverter.convert(result);
        return QuizManageListResponse.builder()
                .total(result.getTotal())
                .start(start)
                .count(result.getCount())
                .quizList(quizAll)
                .build();
    }
}
