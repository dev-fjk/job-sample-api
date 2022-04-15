package com.gw.job.sample.presentation.converter;

import com.gw.job.sample.domain.model.result.QuizManageResult;
import com.gw.job.sample.domain.model.result.QuizQuestionResult;
import com.gw.job.sample.infrastructure.model.entity.Answer;
import com.gw.job.sample.presentation.model.response.QuizManageListResponse.QuizAll;
import com.gw.job.sample.presentation.model.response.QuizQuestionListResponse.QuizQuestion;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizConverter {

    private final ModelMapper modelMapper;

    /**
     * QuizQuestionResultからレスポンスに設定するクイズ一覧への変換を行う
     *
     * @param result : クイズ取得結果
     * @return クイズ一覧
     */
    public List<QuizQuestion> convert(QuizQuestionResult result) {

        // クイズIDと回答のMapを作成する
        final var quizIdPairAnswerMap = this.createQuizIdPairAnswerMap(result.getAnswerList());

        return result.getQuizList().stream().map(quiz -> {
            final Answer tmpAnswer = quizIdPairAnswerMap.get(quiz.getQuizId());
            return QuizQuestion.builder()
                    .quizId(quiz.getQuizId())
                    .question(quiz.getQuestion())
                    .commentary(quiz.getCommentary())
                    .answer(modelMapper.map(tmpAnswer, QuizQuestion.QuizAnswer.class))
                    .build();
        }).collect(Collectors.toList());
    }

    /**
     * QuizManageResultからレスポンスに設定するクイズ一覧への変換を行う
     *
     * @param result : クイズ取得結果
     * @return クイズ一覧
     */
    public List<QuizAll> convert(QuizManageResult result) {

        // クイズIDと回答のMapを作成する
        final var quizIdPairAnswerMap = this.createQuizIdPairAnswerMap(result.getAnswerList());

        return result.getQuizList().stream().map(quiz -> {
            final Answer tmpAnswer = quizIdPairAnswerMap.get(quiz.getQuizId());
            return QuizAll.builder()
                    .quizId(quiz.getQuizId())
                    .question(quiz.getQuestion())
                    .commentary(quiz.getCommentary())
                    .quizStatus(quiz.getQuizStatus().getDisplayName())
                    .answer(modelMapper.map(tmpAnswer, QuizAll.QuizManageAnswer.class))
                    .build();
        }).collect(Collectors.toList());
    }

    /**
     * QuizIDと回答のマップを作成して返却する
     *
     * @param answerList : 回答一覧
     * @return キー クイズID 値 AnswerのMap
     */
    private Map<Long, Answer> createQuizIdPairAnswerMap(List<Answer> answerList) {
        return answerList.stream().collect(
                Collectors.toMap(Answer::getQuizId, UnaryOperator.identity())
        );
    }
}
