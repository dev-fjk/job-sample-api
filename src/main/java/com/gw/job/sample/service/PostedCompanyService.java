package com.gw.job.sample.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gw.job.sample.converter.PostedResponseConverter;
import com.gw.job.sample.entity.request.EmployeeUpdateRequest;
import com.gw.job.sample.entity.request.PostedUpdateRequest;
import com.gw.job.sample.entity.response.EmployeeResponse;
import com.gw.job.sample.entity.response.PostedResponse;
import com.gw.job.sample.exception.ResourceNotFoundException;
import com.gw.job.sample.factory.PostedCompanyFactory;
import com.gw.job.sample.repository.PostedCompanyRepository;

import lombok.RequiredArgsConstructor;
import lombok.var;

/**
 * 応募情報を取得するServiceクラス
 */
@Service
@RequiredArgsConstructor
public class PostedCompanyService {
	

	private final PostedCompanyRepository postedCompanyRepository;
	private final PostedResponseConverter postedResponseConverter;
	private final PostedCompanyFactory postedCompanyFactory;
	
    /**
     * 応募情報を取得する
     * 
     * @param userId 	ユーザID
     * @param companyId 企業ID
     * @return {@link PostedResponse} 応募情報
     */
    public PostedResponse findOne(long userId, long companyId) {
        var postedCompany = postedCompanyRepository.findOne(userId, companyId)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("応募情報が見つかりません。");
                });
        
        return postedResponseConverter.convert(postedCompany);
    }
    
    /**
     * 応募情報を追加する
     * 
     * @param userId 	ユーザID
     * @param companyId 企業ID
     * @return {@link PostedResponse} 応募情報
     */
    @Transactional(rollbackFor = Throwable.class)
    public PostedResponse add(long userId, long companyId) {
    	var addPostedCompany = postedCompanyFactory.createAddPostedCompany(userId, companyId);
		var response = postedCompanyRepository.insert(addPostedCompany);
		
		return postedResponseConverter.convert(response);
    }
    
    /**
     * 社員情報を更新する
     *
     * @param userId    ユーザID
     * @param companyId 企業ID
     * @param updateRequest 更新リクエスト
     * @return 更新した応募情報
     */
    @Transactional(rollbackFor = Throwable.class)
    public PostedResponse update(long userId, long companyId, PostedUpdateRequest updateRequest) {
		System.out.println("更新メソッドの起動");
		postedCompanyRepository.findOneForUpdate(userId, companyId)
		.orElseThrow(() -> {
			throw new ResourceNotFoundException("応募情報が見つかりません");
		});
		var savePostedCompany = postedCompanyFactory.createUpdatePostedCompany(userId, companyId, updateRequest);
    	var updatePostedCompany = postedCompanyRepository.update(savePostedCompany);
		return postedResponseConverter.convert(updatePostedCompany);

        // 更新対象の社員に対してレコードロックをかける
        // 1テーブルへの更新なので無理にロックする必要もあまりないが、 参考用に悲観ロックを行っている

    }
    
    /**
     * 応募情報を削除する
     *
     * @param userId    ユーザID
     * @param companyId 企業ID
     * @throws ResourceNotFoundException 削除対象が見つからない場合throw
     */
    @Transactional(rollbackFor = Throwable.class)
    public void delete(long userId, long companyId) {
    	boolean isDelete = postedCompanyRepository.deleteByUserIdAndCompanyId(userId, companyId);
    	if (!isDelete) {
    		throw new ResourceNotFoundException("応募情報が見つかりません　ID: " + userId + companyId);
    	}
    }

    

}
