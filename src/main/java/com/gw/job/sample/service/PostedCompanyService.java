package com.gw.job.sample.service;

import org.springframework.stereotype.Service;

import com.gw.job.sample.converter.PostedResponseConverter;
import com.gw.job.sample.entity.request.PostedAddRequest;
import com.gw.job.sample.entity.response.PostedResponse;
import com.gw.job.sample.exception.ResourceNotFoundException;
import com.gw.job.sample.factory.PostedCompanyFactory;
import com.gw.job.sample.repository.PostedCompanyRepository;
import lombok.RequiredArgsConstructor;

/**
 * 応募情報 サービス
 */
@Service
@RequiredArgsConstructor
public class PostedCompanyService {
    
    private final PostedCompanyRepository postedCompanyRepository;
    private final PostedCompanyFactory postedCompanyFactory;
    private final PostedResponseConverter postedResponseConverter;

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
        
        return postedResponseConverter.convert(postedCompany);
    }

    /**
     * 応募情報を追加する
     * @param userId ユーザID
     * @param companyId 企業ID
     * @param addRequest 応募情報追加リクエスト
     * @return 応募情報
     */
    public PostedResponse add(long userId, long companyId, PostedAddRequest addRequest) {
        var postedCompany = postedCompanyFactory.createAddPostedCompany(userId, companyId, addRequest);
        postedCompanyRepository.insert(postedCompany);
        return postedResponseConverter.convert(postedCompany);
    }
}
