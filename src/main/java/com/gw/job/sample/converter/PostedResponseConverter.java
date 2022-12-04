package com.gw.job.sample.converter;

import org.springframework.stereotype.Component;

import com.gw.job.sample.entity.doma.PostedCompany;
import com.gw.job.sample.entity.response.PostedResponse;

/**
 * 応募情報を変換するConverterクラス
 */
@Component
public class PostedResponseConverter {

    /**
     * 応募情報のレスポンスを作成する
     *
     * @param postedCompany 応募情報
     * @return {@link PostedResponse}
     */
	public PostedResponse convert(PostedCompany postedCompany) {
		return PostedResponse.builder()
				.companyId(postedCompany.getCompanyId())
				.userId(postedCompany.getUserId())
				.status(postedCompany.getStatus())
				.entryDate(postedCompany.getEntryDate())
				.build();
	}
}
