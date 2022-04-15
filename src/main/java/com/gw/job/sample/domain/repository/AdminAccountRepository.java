package com.gw.job.sample.domain.repository;

public interface AdminAccountRepository {

    /**
     * ログイン処理を行い認証チェックを行う
     *
     * @param accountId    : 管理者アカウントID
     * @param hashPassword : ハッシュ化済みのパスワード
     * @return 認証結果
     */
    boolean login(String accountId, String hashPassword);
}
