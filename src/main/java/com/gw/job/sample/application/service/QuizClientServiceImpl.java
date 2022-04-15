package com.gw.job.sample.application.service;

import com.gw.job.sample.application.exception.QuizNotEnoughCountException;
import com.gw.job.sample.application.exception.RepositoryControlException;
import com.gw.job.sample.domain.model.consts.QuizStatus;
import com.gw.job.sample.domain.model.dto.QuizAddDto;
import com.gw.job.sample.domain.model.result.QuizQuestionResult;
import com.gw.job.sample.domain.repository.AnswerRepository;
import com.gw.job.sample.domain.repository.QuizRepository;
import com.gw.job.sample.domain.service.QuizClientService;
import com.gw.job.sample.infrastructure.model.entity.Quiz;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizClientServiceImpl implements QuizClientService {

    private final QuizRepository quizRepository;
    private final AnswerRepository answerRepository;

    /**
     * クライアント向けのクイズ問題の取得を行う
     *
     * @param count : 取得指定件数
     * @return クイズ取得結果
     */
    @Override
    public QuizQuestionResult fetchQuiz(int count) {

        final var fetchQuizResult = quizRepository.fetchRandomQuiz(count);
        final var quizList = fetchQuizResult.getQuizList();
        if (quizList.size() < count) {
            throw new QuizNotEnoughCountException("指定された件数分のクイズが見つかりません");
        }

        final List<Long> quizIdList = quizList.stream()
                .map(Quiz::getQuizId)
                .collect(Collectors.toList());

        return QuizQuestionResult.builder()
                .quizList(quizList)
                .answerList(answerRepository.fetchByQuizIdList(quizIdList))
                .build();
    }

    /**
     * クイズの追加リクエストを行う
     *
     * @param quizAddDto : クイズ追加DTO
     */
    @Override
    @Transactional(rollbackFor = Throwable.class, timeout = 15)
    public void quizRequest(QuizAddDto quizAddDto) {

        // client側から登録する際はリクエストで固定する
        quizAddDto.setStatus(QuizStatus.REQUEST);

        final Quiz insertedQuiz = quizRepository.insertQuiz(quizAddDto);
        boolean isInsertedAnswer = answerRepository.insertAnswer(insertedQuiz.getQuizId(), quizAddDto.getAnswer());
        if (!isInsertedAnswer) {
            throw new RepositoryControlException("回答の登録に失敗しました");
        }
    }
}
