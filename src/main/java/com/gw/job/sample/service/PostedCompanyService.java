package com.gw.job.sample.service;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.job.sample.entity.response.PostedResponse;
import com.gw.job.sample.repository.PostedCompanyRepository;

@Service
public class PostedCompanyService {
	
	@Autowired
	private PostedCompanyRepository postedCompanyRepository;
	
    public PostedResponse findOne(long userId, long companyId) {
    	System.out.println("serviceが起動しました");
    	
    	PostedResponse response = postedCompanyRepository.findOne(userId, companyId);
    	
    	
    	// TODO データベースからデータを持ってきて変換する
        return PostedResponse.builder()
        		.userId(userId)
        		.companyId(companyId)
        		.status(1)
        		.entryDate(LocalDate.now())
        		.build();
    }

}
