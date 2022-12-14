package com.gw.job.sample.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gw.job.sample.converter.PostedResponseConverter;
import com.gw.job.sample.entity.response.PostedResponse;
import com.gw.job.sample.exception.ResourceNotFoundException;
import com.gw.job.sample.factory.PostedCompanyFactory;
import com.gw.job.sample.repository.PostedCompanyRepository;

import lombok.RequiredArgsConstructor;
import lombok.var;

/**
 * 応募情報を取得するServiceクラス
 */
@Service
@RequiredArgsConstructor
public class PostedCompanyService {
	

	private final PostedCompanyRepository postedCompanyRepository;
	private final PostedResponseConverter postedResponseConverter;
	private final PostedCompanyFactory postedCompanyFactory;
	
    /**
     * 応募情報を取得する
     * 
     * @param userId 	ユーザID
     * @param companyId 企業ID
     * @return {@link PostedResponse} 応募情報
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
     * 
     * @param userId 	ユーザID
     * @param companyId 企業ID
     * @return {@link PostedResponse} 応募情報
     */
    @Transactional(rollbackFor = Throwable.class)
    public PostedResponse add(long userId, long companyId) {
    	var addPostedCompany = postedCompanyFactory.createAddPostedCompany(userId, companyId);
		var response = postedCompanyRepository.insert(addPostedCompany);
		
		return postedResponseConverter.convert(response);
    }
    
    

}
