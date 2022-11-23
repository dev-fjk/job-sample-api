package com.gw.job.sample.repository;


import org.springframework.stereotype.Repository;

import com.gw.job.sample.dao.EmployeeDao;
import com.gw.job.sample.dao.PostedCompanyDao;
import com.gw.job.sample.entity.doma.PostedCompany;
import com.gw.job.sample.entity.response.PostedResponse;

import groovyjarjarantlr4.v4.parse.ANTLRParser.finallyClause_return;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostedCompanyRepository {
	
	private final PostedCompanyDao postedCompanyDao;

	public PostedResponse findOne(long userId, long companyId) {
		
		System.out.println("Repositoryが起動しました");
		PostedCompany response = postedCompanyDao.findByUserIdAndCompanyId(userId, companyId);
		System.out.println("DB抽出完了");
		return null;
	}
	
	
}
