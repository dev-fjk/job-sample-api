package com.gw.job.sample.converter;

import com.gw.job.sample.entity.response.EmptyResponse;
import com.gw.job.sample.entity.response.ResumeResponse;
import com.gw.job.sample.entity.result.ResumeResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * レスポンスへの変換を行うクラス
 */
@Component
@RequiredArgsConstructor
public class ResponseConverter {

    /**
     * レジュメ取得結果のレスポンスに変換する
     *
     * @param result レジュメ取得結果
     * @return レジュメ取得結果レスポンス
     */
    public ResumeResponse convertResumeResponse(ResumeResult result) {
        return ResumeResponse.builder()
                .userId(result.getUserId())
                .lastName(result.getLastName())
                .firstName(result.getFirstName())
                .birthDate(result.getBirthDate())
                .jobDescription(result.getJobDescription())
                .build();
    }

    /**
     * 空レスポンスを返す
     *
     * @return 空レスポンス
     */
    public EmptyResponse empty() {
        return new EmptyResponse();
    }
}
