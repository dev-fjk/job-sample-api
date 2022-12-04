package com.gw.job.sample.service;


import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.job.sample.converter.EmployeeResponseConverter;
import com.gw.job.sample.converter.PostedResponseConverter;
import com.gw.job.sample.entity.doma.PostedCompany;
import com.gw.job.sample.entity.response.PostedResponse;
import com.gw.job.sample.exception.ResourceNotFoundException;
import com.gw.job.sample.factory.EmployeeFactory;
import com.gw.job.sample.repository.EmployeeRepository;
import com.gw.job.sample.repository.PostedCompanyRepository;

import lombok.RequiredArgsConstructor;

/**
 * 応募情報を取得するServiceクラス
 */
@Service
@RequiredArgsConstructor
public class PostedCompanyService {
	

	private final PostedCompanyRepository postedCompanyRepository;
	private final PostedResponseConverter postedResponseConverter;
	
    /**
     * 応募情報を取得する
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
    
    
    
    

}
