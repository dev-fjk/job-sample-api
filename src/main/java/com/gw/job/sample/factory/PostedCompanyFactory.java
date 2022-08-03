package com.gw.job.sample.factory;

import com.gw.job.sample.entity.doma.PostedCompany;
import com.gw.job.sample.entity.enums.PostedStatus;
import com.gw.job.sample.entity.request.PostedUpdateRequest;

import org.springframework.stereotype.Component;

/**
 * 応募情報作成を行うクラス
 */
@Component
public class PostedCompanyFactory {

    /**
     * 応募情報追加用のオブジェクトを作成する
     * @param userId ユーザID
     * @param companyId 企業ID
     * @param addRequest 応募情報追加リクエスト
     * @return 応募情報追加用オブジェクト
     */
    public PostedCompany createAddPostedCompany(long userId, long companyId) {
        final PostedCompany postedCompany = new PostedCompany();
        postedCompany.setUserId(userId);
        postedCompany.setCompanyId(companyId);
        postedCompany.setStatus(PostedStatus.BEFORE_SELECTION);
        // 応募するのはユーザ側のため、ユーザIDを作成者・更新者カラムに挿入
        postedCompany.setCreatedBy(String.valueOf(userId));
        postedCompany.setUpdatedBy(String.valueOf(userId));
        return postedCompany;
    }

    /**
     * 応募情報更新用のオブジェクトを作成する
     * @param userId ユーザID
     * @param companyId 企業ID
     * @param updateRequest 応募情報追加リクエスト
     * @return 応募情報更新用オブジェクト
     */
    public PostedCompany createUpdatePostedCompany(long userId, long companyId, PostedUpdateRequest updateRequest) {
        final PostedCompany postedCompany = new PostedCompany();
        postedCompany.setUserId(userId);
        postedCompany.setCompanyId(companyId);
        postedCompany.setStatus(PostedStatus.of(updateRequest.getStatus()));
        postedCompany.setEntryDate(updateRequest.getEntryDate());
        // ステータス更新権限があるのは企業側という仕様のため、ユーザIDではなく企業IDを挿入
        postedCompany.setUpdatedBy(String.valueOf(companyId));
        return postedCompany;
    }
}
