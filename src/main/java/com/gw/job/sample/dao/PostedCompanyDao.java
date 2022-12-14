package com.gw.job.sample.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import com.gw.job.sample.entity.doma.PostedCompany;
import com.gw.job.sample.entity.response.PostedResponse;

@Dao
@ConfigAutowireable
public interface PostedCompanyDao {

    /**
     * 応募情報を取得する
     * 
     * @param userId    ユーザーID
     * @param companyId 企業ID
     * @return {@link PostedResponse} が設定されたResponseEntity
     */
    @Select
    PostedCompany findByUserIdAndCompanyId(long userId, long companyId);
    
    /**
     * 応募情報を追加する
     * 
     * @param userId    ユーザーID
     * @param companyId 企業ID
     * @return 追加件数
     */
    @Insert(excludeNull = true)
    int insert(PostedCompany postedCompany);

    /***
     * 応募情報を検索オプション付きで取得する
     *
     * @param employeeId 社員ID
     * @param companyId 企業ID
     * @return 応募情報
     */
    @Select
    PostedCompany findWithOptions(long userId, long companyId, SelectOptions options);

    /**
     * 応募情報を更新する
     *
     * @param postedCompany 更新する応募情報
     * @return 更新件数
     */
    @Update
    int update(PostedCompany postedCompany);
}
