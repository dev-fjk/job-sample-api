package com.gw.job.sample.repository;

import com.gw.job.sample.dao.ResumeDao;
import com.gw.job.sample.entity.result.ResumeResult;
import com.gw.job.sample.repository.interfaces.ResumeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ResumeRepositoryImpl implements ResumeRepository {

    private final ResumeDao resumeDao;

    /**
     * ユーザーのレジュメを取得する
     *
     * @param userId ユーザーID
     * @return レジュメ取得結果 見つからない場合Optional.emptyを返却
     */
    @Override
    public Optional<ResumeResult> fetchUserResume(long userId) {
        var resume = resumeDao.findByUserId(userId);
        return Optional.ofNullable(ResumeResult.of(resume));
    }
}
