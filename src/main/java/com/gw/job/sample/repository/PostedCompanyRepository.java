package com.gw.job.sample.repository;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import com.gw.job.sample.dao.PostedCompanyDao;
import com.gw.job.sample.entity.doma.PostedCompany;
import com.gw.job.sample.exception.RepositoryControlException;

/**
 * 応募情報テーブル Repository
 */
@Repository
@RequiredArgsConstructor
public class PostedCompanyRepository {
    
    private final PostedCompanyDao postedCompanyDao;

    /**
     * 応募情報を取得する
     * @param userId ユーザID
     * @param companyId 企業ID
     * @return {@link PostedCompany} 応募情報
     */
    public Optional<PostedCompany> findOne(long userId, long companyId) {
        var postedCompany = postedCompanyDao.findByUserIdAndCompanyId(userId, companyId);
        return Optional.ofNullable(postedCompany);
    }

    /**
     * 応募情報を取得した上で悲観ロックをかける
     * @param userId ユーザID
     * @param companyId 企業ID
     * @return
     */
    public Optional<PostedCompany> findOneForUpdate(long userId, long companyId) {
        var options = SelectOptions.get().forUpdate();
        var postedCompany = postedCompanyDao.findByUserIdAndCompanyIdWithOptions(userId, companyId, options);
        return Optional.ofNullable(postedCompany);
    }

    /**
     * 応募情報を追加する
     * @param postedCompany 追加する応募情報
     * @return {@link PostedCompany} 応募情報
     */
    public PostedCompany insert(PostedCompany postedCompany) {
        int insertCount = postedCompanyDao.insert(postedCompany);
        if(insertCount <= 0) {
            throw new RepositoryControlException("データの追加に失敗しました");
        }
        return postedCompany;
    }
    
    /**
     * 応募情報を更新する
     * @param postedCompany 更新する応募情報
     * @return {@link PostedCompany} 応募情報
     */
    public PostedCompany update(PostedCompany postedCompany) {
        int updateCount = postedCompanyDao.update(postedCompany);
        if(updateCount <= 0) {
            throw new RepositoryControlException("データの更新に失敗しました");
        }
        return postedCompanyDao.findByUserIdAndCompanyId(postedCompany.getUserId(), postedCompany.getCompanyId());
    }
}
