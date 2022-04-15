package com.gw.job.sample.application.service;

import com.gw.job.sample.application.exception.RepositoryControlException;
import com.gw.job.sample.application.exception.ResourceNotFoundException;
import com.gw.job.sample.domain.model.consts.QuizStatus;
import com.gw.job.sample.domain.model.dto.QuizAddDto;
import com.gw.job.sample.domain.model.dto.QuizUpdateListDto;
import com.gw.job.sample.domain.model.result.FetchQuizResult;
import com.gw.job.sample.domain.model.result.QuizManageResult;
import com.gw.job.sample.domain.repository.AnswerRepository;
import com.gw.job.sample.domain.repository.QuizRepository;
import com.gw.job.sample.domain.service.QuizAdminService;
import com.gw.job.sample.infrastructure.model.entity.Quiz;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizAdminServiceImpl implements QuizAdminService {

    private final QuizRepository quizRepository;
    private final AnswerRepository answerRepository;

    /**
     * クイズ一覧の取得を行う
     *
     * @param start : offset 取得開始
     * @param count : limit 取得件数
     * @return 取得結果
     */
    @Override
    public QuizManageResult fetchQuiz(int start, int count) {
        var fetchQuizResult = quizRepository.fetchQuiz(start, count);
        return this.commonFetchManageQuiz(fetchQuizResult, start);
    }

    /**
     * リクエスト中のクイズ一覧の取得を行う
     *
     * @param start : offset 取得開始
     * @param count : limit 取得件数
     * @return 取得結果
     */
    @Override
    public QuizManageResult fetchRequestQuiz(int start, int count) {
        var fetchQuizResult = quizRepository.fetchQuiz(start, count, QuizStatus.REQUEST);
        return this.commonFetchManageQuiz(fetchQuizResult, start);
    }

    /**
     * クイズの追加を行う
     *
     * @param quizAddDto : クイズ追加Dao
     */
    @Override
    @Transactional(rollbackFor = Throwable.class, timeout = 15)
    public void insertQuiz(QuizAddDto quizAddDto) {

        // 管理者側から追加する際は有効で登録
        quizAddDto.setStatus(QuizStatus.ENABLED);

        final Quiz insertedQuiz = quizRepository.insertQuiz(quizAddDto);
        boolean isInsertedAnswer = answerRepository.insertAnswer(insertedQuiz.getQuizId(), quizAddDto.getAnswer());
        if (!isInsertedAnswer) {
            throw new RepositoryControlException("回答の登録に失敗しました");
        }
    }

    /**
     * クイズの更新を行う
     *
     * @param quizUpdateListDto : 更新するクイズ情報一覧を保持したDto
     */
    @Override
    @Transactional(rollbackFor = Throwable.class, timeout = 15)
    public void updateQuiz(QuizUpdateListDto quizUpdateListDto) {

        var requestQuizIdSet = quizUpdateListDto.getQuizList().stream()
                .map(QuizUpdateListDto.QuizUpdateDto::getQuizId).collect(Collectors.toSet());

        // リクエストで受領したクイズIDを持つレコードに対して悲観ロックを掛ける
        var fetchQuizResult = quizRepository.fetchByQuizIdSetForUpdate(requestQuizIdSet);
        if (CollectionUtils.isEmpty(fetchQuizResult.getQuizList())) {
            throw new ResourceNotFoundException("更新対象のクイズが見つかりません");
        }

        // リクエストで受領したクイズ更新DtoのIDが実際にDBから取得したID一覧に含まれるIDかどうかチェックを行い
        // DBに存在するIDを持つDtoのみを抽出した上で更新処理を実行する
        var fetchQuizIdSet = fetchQuizResult.getQuizList()
                .stream().map(Quiz::getQuizId).collect(Collectors.toSet());

        List<QuizUpdateListDto.QuizUpdateDto> updateDtoList = quizUpdateListDto.getQuizList()
                .stream()
                .filter(quizUpdateDto -> fetchQuizIdSet.contains(quizUpdateDto.getQuizId()))
                .collect(Collectors.toList());

        updateDtoList.forEach(quizUpdateDto -> {

            boolean isQuizUpdate = quizRepository.updateQuiz(quizUpdateDto);
            boolean isAnswerUpdate = answerRepository.updateAnswer(
                    quizUpdateDto.getQuizId(), quizUpdateDto.getAnswer()
            );
            if (!isQuizUpdate || !isAnswerUpdate) {
                throw new RepositoryControlException("クイズの更新に失敗しました クイズID: " + quizUpdateDto.getQuizId());
            }
        });
    }

    /**
     * クイズの削除を行う
     *
     * @param quizIdList : 削除対象のクイズのクイズIDリスト
     */
    @Override
    @Transactional(rollbackFor = Throwable.class, timeout = 15)
    public void deleteQuiz(List<Long> quizIdList) {
        answerRepository.deleteByQuizIdList(quizIdList);
        quizRepository.deleteByQuizIdList(quizIdList);
    }

    /**
     * クイズ一覧を取得する際の共通処理を行う
     *
     * @param fetchQuizResult : クイズテーブルの取得結果
     * @param start           : 取得開始位置
     * @return {@link QuizManageResult}
     */
    private QuizManageResult commonFetchManageQuiz(FetchQuizResult fetchQuizResult, int start) {

        if (fetchQuizResult.getTotal() == 0) {
            throw new ResourceNotFoundException("クイズが見つかりませんでした");
        }
        if (CollectionUtils.isEmpty(fetchQuizResult.getQuizList())) {
            // 取得結果が0件の場合は空のレスポンスを返却する
            return QuizManageResult.builder()
                    .total(fetchQuizResult.getTotal())
                    .start(start)
                    .quizList(Collections.emptyList())
                    .answerList(Collections.emptyList())
                    .build();
        }

        // 取得したクイズのIDを条件に回答一覧を取得する
        var quizList = fetchQuizResult.getQuizList();
        final List<Long> quizIdList = quizList.stream()
                .map(Quiz::getQuizId)
                .collect(Collectors.toList());

        return QuizManageResult.builder()
                .total(fetchQuizResult.getTotal())
                .start(start)
                .quizList(quizList)
                .answerList(answerRepository.fetchByQuizIdList(quizIdList))
                .build();
    }
}
