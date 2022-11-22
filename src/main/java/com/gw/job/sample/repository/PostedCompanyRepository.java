package com.gw.job.sample.repository;


import org.springframework.stereotype.Repository;

import com.gw.job.sample.entity.response.PostedResponse;

@Repository
public class PostedCompanyRepository {

	public PostedResponse findOne(long userId, long companyId) {
		
		System.out.println("Repositoryが起動しました");
		
		return null;
	}
	
	
}
