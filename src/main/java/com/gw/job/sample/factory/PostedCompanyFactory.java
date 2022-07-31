package com.gw.job.sample.factory;

import com.gw.job.sample.entity.doma.PostedCompany;
import com.gw.job.sample.entity.enums.PostedStatus;

import org.springframework.stereotype.Component;

/**
 * 応募情報作成を行うクラス
 */
@Component
public class PostedCompanyFactory {

    /**
     * 追加する応募情報を作成する
     * @param userId ユーザID
     * @param companyId 企業ID
     * @param addRequest 応募情報追加リクエスト
     * @return 追加する応募情報
     */
    public PostedCompany createAddPostedCompany(long userId, long companyId) {
        final PostedCompany postedCompany = new PostedCompany();
        postedCompany.setUserId(userId);
        postedCompany.setCompanyId(companyId);
        postedCompany.setStatus(PostedStatus.BEFORE_SELECTION);
        postedCompany.setCreatedBy(String.valueOf(userId));
        postedCompany.setUpdatedBy(String.valueOf(userId));
        return postedCompany;
    }
}
