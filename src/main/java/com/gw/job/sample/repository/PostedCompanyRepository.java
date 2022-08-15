package com.gw.job.sample.repository;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DuplicateKeyException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import com.gw.job.sample.dao.PostedCompanyDao;
import com.gw.job.sample.entity.doma.PostedCompany;
import com.gw.job.sample.exception.RepositoryControlException;
import com.gw.job.sample.exception.ResourceAlreadyExistException;


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
     * @return {@link PostedCompany} 追加した応募情報
     */
    public PostedCompany insert(PostedCompany postedCompany) {
        try {
            int insertCount = postedCompanyDao.insert(postedCompany);
            if(insertCount != 1) {
                throw new RepositoryControlException("データの追加に失敗しました");
            }

            return postedCompanyDao.findByUserIdAndCompanyId(postedCompany.getUserId(), postedCompany.getCompanyId());
        } catch(DuplicateKeyException exception) {
            // 409: conflictのエラーを返す
            throw new ResourceAlreadyExistException("既に応募している企業です error: " + exception.getMessage());
        }
    }
    
    /**
     * 応募情報を更新する
     * @param postedCompany 更新する応募情報
     * @return {@link PostedCompany} 更新した応募情報
     */
    public PostedCompany update(PostedCompany postedCompany) {
        int updateCount = postedCompanyDao.update(postedCompany);
        if(updateCount != 1) {
            throw new RepositoryControlException("データの更新に失敗しました");
        }

        return postedCompanyDao.findByUserIdAndCompanyId(postedCompany.getUserId(), postedCompany.getCompanyId());
    }

    /**
     * 応募情報を削除する
     * @param userId ユーザID
     * @param companyId 企業ID
     * @return 削除結果(true: 削除成功、false: 削除対象が見つからない)
     */
    public boolean delete(long userId, long companyId) {
        int deleteCount = postedCompanyDao.deleteByUserIdAndCompanyId(userId, companyId);
        return deleteCount == 1;
    }
}
