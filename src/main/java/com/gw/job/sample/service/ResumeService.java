package com.gw.job.sample.service;

import com.gw.job.sample.entity.response.ResumeResponse;
import com.gw.job.sample.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * レジュメ関連処理を行うService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;

    /**
     * ユーザーのレジュメを取得する
     *
     * @param userId ユーザーID
     * @return {@link ResumeResponse} レジュメが取得できなかった場合nullを返す
     */
    public ResumeResponse findOne(long userId) {
        var resumeResult = resumeRepository.findOne(userId);
        return resumeResult.map(result ->
                ResumeResponse.builder()
                        .userId(result.getUserId())
                        .lastName(result.getLastName())
                        .firstName(result.getFirstName())
                        .birthDate(result.getBirthDate())
                        .jobDescription(result.getJobDescription())
                        .build()
        ).orElse(null);
    }
}
