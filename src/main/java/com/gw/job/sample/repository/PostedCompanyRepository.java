package com.gw.job.sample.repository;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gw.job.sample.dao.PostedCompanyDao;
import com.gw.job.sample.entity.doma.PostedCompany;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostedCompanyRepository {
	
	@Autowired
	private final PostedCompanyDao postedCompanyDao;


	public Optional<PostedCompany> findOne(long userId, long companyId) {
		var response = postedCompanyDao.findByUserIdAndCompanyId(userId, companyId);
		return Optional.ofNullable(response);
	}
	
	
}
