package com.elite.miko.quiz.infrastructure.repository;

import com.elite.miko.quiz.domain.repository.AdminAccountRepository;
import com.elite.miko.quiz.infrastructure.dao.AdminAccountDao;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminAccountRepositoryImpl implements AdminAccountRepository {

    private final AdminAccountDao accountDao;

    /**
     * ログイン処理を行い認証チェックを行う
     *
     * @param accountId    : 管理者アカウントID
     * @param hashPassword : ハッシュ化済みのパスワード
     * @return 認証結果
     */
    @Override
    public boolean login(@NonNull String accountId, @NonNull String hashPassword) {
        return accountDao.countByAccountIdAndPassword(accountId, hashPassword) == 1;
    }
}
