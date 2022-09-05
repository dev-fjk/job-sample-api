package com.gw.job.sample.service;


import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.gw.job.sample.entity.response.PostedResponse;

@Service
public class PostedCompanyService {
	
    public PostedResponse findOne(long userId, long companyId) {
    	System.out.println("serviceが起動しました");
    	
    	// TODO データベースからデータを持ってきて変換する
        return PostedResponse.builder()
        		.userId(userId)
        		.companyId(companyId)
        		.status(1)
        		.entryDate(LocalDate.now())
        		.build();
    }

}
