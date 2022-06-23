package com.gw.job.sample.repository;

import com.gw.job.sample.dao.ResumeDao;
import com.gw.job.sample.entity.result.ResumeResult;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * レジュメテーブルの操作を行うRepository
 */
@Repository
@RequiredArgsConstructor
public class ResumeRepository {

    private final ResumeDao resumeDao;

    /**
     * ユーザーのレジュメを取得する
     *
     * @param userId ユーザーID
     * @return レジュメ取得結果 見つからない場合Optional.emptyを返却
     */
    public Optional<ResumeResult> fetchUserResume(long userId) {
        var resume = resumeDao.findByUserId(userId);
        return Optional.ofNullable(ResumeResult.of(resume));
    }
}
