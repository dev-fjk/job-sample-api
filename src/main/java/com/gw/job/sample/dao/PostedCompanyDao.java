package com.gw.job.sample.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.gw.job.sample.entity.doma.PostedCompany;
import com.gw.job.sample.entity.response.PostedResponse;

@Dao
@ConfigAutowireable
public interface PostedCompanyDao {

    /**
     * 応募情報を取得する
     * @param userId    ユーザーID
     * @param companyId 企業ID
     * @return {@link PostedResponse} が設定されたResponseEntity
     */
	
    @Select
    PostedCompany findByUserIdAndCompanyId(long userId, long companyId);

}
