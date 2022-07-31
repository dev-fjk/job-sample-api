package com.gw.job.sample.converter;

import com.gw.job.sample.entity.doma.PostedCompany;
import com.gw.job.sample.entity.response.PostedResponse;
import org.springframework.stereotype.Component;

/**
 * 社員レスポンスへ変換するconverter
 */
@Component
public class PostedResponseConverter {

    /**
     * 社員レスポンスを作成する
     *
     * @param postedCompany 社員
     * @return {@link postedCompanyResponse}
     */
    public PostedResponse convert(PostedCompany postedCompany) {
        return PostedResponse.builder()
                .companyId(postedCompany.getCompanyId())
                .userId(postedCompany.getUserId())
                .status(postedCompany.getStatus().getValue())
                .entryDate(postedCompany.getEntryDate())
                .build();
    }
}
