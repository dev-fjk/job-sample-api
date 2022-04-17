package com.gw.job.sample.service;

import com.gw.job.sample.entity.result.ResumeResult;
import com.gw.job.sample.repository.interfaces.ResumeRepository;
import com.gw.job.sample.service.interfaces.ResumeService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;

    /**
     * ユーザーのレジュメを取得する
     *
     * @param userId ユーザーID
     * @return レジュメ取得結果 見つからない場合Optional.emptyを返却
     */
    @Override
    public Optional<ResumeResult> fetchUserResume(long userId) {
        return resumeRepository.fetchUserResume(userId);
    }
}
