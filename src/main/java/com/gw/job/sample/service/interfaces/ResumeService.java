package com.gw.job.sample.service.interfaces;

import com.gw.job.sample.entity.result.ResumeResult;
import java.util.Optional;

public interface ResumeService {

    /**
     * ユーザーのレジュメを取得する
     *
     * @param userId ユーザーID
     * @return レジュメ取得結果 見つからない場合Optional.emptyを返却
     */
    Optional<ResumeResult> fetchUserResume(long userId);
}
