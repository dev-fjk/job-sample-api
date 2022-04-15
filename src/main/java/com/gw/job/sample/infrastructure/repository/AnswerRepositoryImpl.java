package com.gw.job.sample.infrastructure.repository;

import com.gw.job.sample.domain.repository.AnswerRepository;
import com.gw.job.sample.infrastructure.dao.AnswerDao;
import com.gw.job.sample.infrastructure.model.entity.Answer;
import com.gw.job.sample.domain.model.dto.QuizAddDto;
import com.gw.job.sample.domain.model.dto.QuizUpdateListDto;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AnswerRepositoryImpl implements AnswerRepository {

    private final AnswerDao answerDao;
    private final ModelMapper modelMapper;

    /**
     * クイズIDの一覧と一致する回答一覧を取得する
     *
     * @param quizIdList : クイズIDのリスト
     * @return 回答一覧
     */
    @Override
    public List<Answer> fetchByQuizIdList(@NonNull List<Long> quizIdList) {
        return answerDao.fetchByQuizIdList(quizIdList);
    }

    /**
     * 回答の追加を行う
     *
     * @param quizId       : クイズID
     * @param answerAddDto : 回答追加Dto
     * @return 更新成功の場合trueを返却
     */
    @Override
    public boolean insertAnswer(long quizId, @NonNull QuizAddDto.AnswerAddDto answerAddDto) {
        final Answer insertAnswer = modelMapper.map(answerAddDto, Answer.class);
        insertAnswer.setQuizId(quizId);
        return answerDao.insert(insertAnswer) == 1;
    }

    /**
     * 回答の更新を行う
     *
     * @param quizId          : クイズID
     * @param answerUpdateDto : 回答更新Dto
     */
    @Override
    public boolean updateAnswer(long quizId, @NonNull QuizUpdateListDto.QuizUpdateDto.AnswerUpdateDto answerUpdateDto) {
        final Answer updateAnswer = modelMapper.map(answerUpdateDto, Answer.class);
        updateAnswer.setQuizId(quizId);
        return answerDao.update(updateAnswer) == 1;
    }

    /**
     * 回答の削除を行う
     *
     * @param quizIdList : 削除対象のクイズのクイズIDリスト
     */
    @Override
    public void deleteByQuizIdList(@NonNull List<Long> quizIdList) {
        answerDao.deleteByQuizIdList(quizIdList);
    }
}
