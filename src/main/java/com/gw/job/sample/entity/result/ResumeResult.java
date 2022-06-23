package com.gw.job.sample.entity.result;

import com.gw.job.sample.entity.Resume;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

/**
 * レジュメ取得結果情報を保持するクラス
 */
@Value
@Builder(access = AccessLevel.PRIVATE)
public class ResumeResult {

    long userId;

    String lastName;

    String firstName;

    LocalDate birthDate;

    String jobDescription;

    /**
     * インスタンスを生成して返す　レジュメが存在しない場合nullを返す
     *
     * @param resume DB レジュメ情報
     * @return {@link ResumeResult}
     */
    public static ResumeResult of(Resume resume) {
        return resume == null
                ? null
                : ResumeResult.builder()
                .userId(resume.getUserId())
                .lastName(resume.getLastName())
                .firstName(resume.getFirstName())
                .birthDate(resume.getBirthDate())
                .jobDescription(resume.getJobDescription())
                .build();
    }
}
