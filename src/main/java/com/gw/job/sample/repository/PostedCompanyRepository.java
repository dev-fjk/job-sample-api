package com.gw.job.sample.repository;

import org.springframework.stereotype.Repository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import com.gw.job.sample.dao.PostedCompanyDao;
import com.gw.job.sample.entity.doma.PostedCompany;

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
        PostedCompany postedCompany = postedCompanyDao.findByUserIdAndCompanyId(userId, companyId);
        return Optional.ofNullable(postedCompany);
    }
}
