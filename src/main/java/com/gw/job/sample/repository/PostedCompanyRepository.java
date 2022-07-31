package com.gw.job.sample.repository;

import org.springframework.stereotype.Repository;
import org.springframework.dao.DuplicateKeyException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import com.gw.job.sample.dao.PostedCompanyDao;
import com.gw.job.sample.entity.doma.PostedCompany;
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
     * @param userId ユーザ情報
     * @param companyId 企業情報
     * @return {@link PostedCompany} 応募情報
     */
    public Optional<PostedCompany> findOne(long userId, long companyId) {
        var postedCompany = postedCompanyDao.findByUserIdAndCompanyId(userId, companyId);
        return Optional.ofNullable(postedCompany);
    }

    /**
     * 応募情報を追加する
     * @param postedCompany 追加する応募情報
     * @return 応募情報追加結果
     */
    public boolean insert(PostedCompany postedCompany) {
        try {
            int insertCount = postedCompanyDao.insert(postedCompany);
            return insertCount == 1;
        } catch(DuplicateKeyException exception) {
            // 409: conflictのエラーを返す
            throw new ResourceAlreadyExistException("既に応募している企業です error: " + exception.getMessage());
        }
    }
}
