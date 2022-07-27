package com.gw.job.sample.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.Insert;
import org.seasar.doma.boot.ConfigAutowireable;

import com.gw.job.sample.entity.doma.PostedCompany;

@Dao
@ConfigAutowireable
public interface PostedCompanyDao {

    /**
     * 応募情報を取得する
     * @param userId ユーザID
     * @param companyId 企業ID
     * @return 応募情報
     */
    @Select
    PostedCompany findByUserIdAndCompanyId(long userId, long companyId);

    /**
     * 応募情報を追加する
     */
    @Insert(excludeNull = true)
    int insert(PostedCompany postedCompany);

    /**
     * 応募情報を更新する
     */
    @Update
    int update(PostedCompany postedCompany);
}
