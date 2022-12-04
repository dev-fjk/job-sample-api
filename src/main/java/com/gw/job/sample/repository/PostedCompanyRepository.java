package com.gw.job.sample.repository;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gw.job.sample.dao.PostedCompanyDao;
import com.gw.job.sample.entity.doma.PostedCompany;
import com.gw.job.sample.entity.response.PostedResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostedCompanyRepository {
	
	@Autowired
	private final PostedCompanyDao postedCompanyDao;

    /**
     * 応募情報を取得する
     * @param userId    ユーザーID
     * @param companyId 企業ID
     * @return {@link PostedResponse} が設定されたResponseEntity
     */
	public Optional<PostedCompany> findOne(long userId, long companyId) {
		var response = postedCompanyDao.findByUserIdAndCompanyId(userId, companyId);
		return Optional.ofNullable(response);
	}
	
	
}
