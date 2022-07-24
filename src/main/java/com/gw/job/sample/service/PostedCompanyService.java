package com.gw.job.sample.service;

import org.springframework.stereotype.Service;

import com.gw.job.sample.entity.response.PostedResponse;
import com.gw.job.sample.exception.ResourceNotFoundException;
import com.gw.job.sample.repository.PostedCompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostedCompanyService {
    
    private final PostedCompanyRepository postedCompanyRepository;

    /**
     * 応募情報を取得する
     * @param userId ユーザID
     * @param companyId 企業ID
     * @return 応募情報
     */
    public PostedResponse findOne(long userId, long companyId) {
        var postedCompany = postedCompanyRepository.findOne(userId, companyId)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("応募情報が見つかりません。");
                });
        
        return PostedResponse.builder()
            .userId(postedCompany.getUserId())
            .companyId(postedCompany.getCompanyId())
            .status(postedCompany.getStatus())
            .entryDate(postedCompany.getEntryDate())
            .build();
    }
}
