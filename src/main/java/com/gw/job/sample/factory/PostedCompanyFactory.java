package com.gw.job.sample.factory;

import org.springframework.stereotype.Component;

import com.gw.job.sample.entity.doma.Employee;
import com.gw.job.sample.entity.doma.PostedCompany;
import com.gw.job.sample.entity.enums.DepartmentCode;
import com.gw.job.sample.entity.enums.PostedCompanyStatus;
import com.gw.job.sample.entity.request.EmployeeAddRequest;

/**
 * 応募情報作成を行うクラス
 */
@Component
public class PostedCompanyFactory {

    /**
     * 追加する応募情報を作成する
     *
     * @param userId    ユーザーID
     * @param companyId 企業ID
     * @return 追加する社員情報
     */
    public PostedCompany createAddPostedCompany(long userId, long companyId) {
        final PostedCompany postedCompany = new PostedCompany();
        postedCompany.setUserId(userId);
        postedCompany.setCompanyId(companyId);
        postedCompany.setStatus(PostedCompanyStatus.BEFORE_SELECTION);
        postedCompany.setCreatedBy(String.valueOf(userId));
        postedCompany.setUpdatedBy(String.valueOf(userId));
        return postedCompany;
    }
}
