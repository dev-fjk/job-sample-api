package com.gw.job.sample.repository;

import java.util.Optional;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.gw.job.sample.dao.PostedCompanyDao;
import com.gw.job.sample.entity.doma.Employee;
import com.gw.job.sample.entity.doma.PostedCompany;
import com.gw.job.sample.entity.request.PostedUpdateRequest;
import com.gw.job.sample.entity.response.PostedResponse;
import com.gw.job.sample.exception.RepositoryControlException;
import com.gw.job.sample.exception.ResourceAlreadyExistException;import groovyjarjarantlr4.v4.parse.ANTLRParser.option_return;
import lombok.RequiredArgsConstructor;
import lombok.var;

@Repository
@RequiredArgsConstructor
public class PostedCompanyRepository {

	@Autowired
	private final PostedCompanyDao postedCompanyDao;

	/**
	 * 応募情報を取得する
	 * 
	 * @param userId    ユーザーID
	 * @param companyId 企業ID
	 * @return {@link PostedResponse} が設定されたResponseEntity
	 */
	public Optional<PostedCompany> findOne(long userId, long companyId) {
		var response = postedCompanyDao.findByUserIdAndCompanyId(userId, companyId);
		return Optional.ofNullable(response);
	}

	/**
	 * 応募情報を追加する
	 * 
	 * @param userId    ユーザーID
	 * @param companyId 企業ID
	 * @return {@link PostedResponse} が設定されたResponseEntity
	 */
	public PostedCompany insert(PostedCompany postedCompany) {
		try {
			var response = postedCompanyDao.insert(postedCompany);
			if (response != 1) {
				throw new RepositoryControlException("データの追加に失敗しました。");
			}
			return postedCompanyDao.findByUserIdAndCompanyId(postedCompany.getUserId(), postedCompany.getCompanyId());
		} catch (DuplicateKeyException exception) {
			throw new ResourceAlreadyExistException("既に応募している企業です error:" + exception.getMessage());
		}
	}
	
    /**
     * 応募情報を取得した上で悲観ロックをかける
     *
	 * @param userId    ユーザーID
	 * @param companyId 企業ID
     * @return {@link PostedCompany} 応募情報
     */
	public Optional<PostedCompany> findOneForUpdate(long userId, long companyId) {
		var options = SelectOptions.get().forUpdate();
		var postedCompany = postedCompanyDao.findWithOptions(userId, companyId, options);
		return Optional.ofNullable(postedCompany);
	}
	
	public PostedCompany update(PostedCompany postedCompany) {
		int updateCount = postedCompanyDao.update(postedCompany);
		if (updateCount != 1) {
			throw new RepositoryControlException("データの更新に失敗しました");
		}
		return postedCompanyDao.findByUserIdAndCompanyId(postedCompany.getCompanyId(), postedCompany.getCompanyId());
		
	}
}
