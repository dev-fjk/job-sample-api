package com.gw.job.sample.domain.service;

public interface AdminAccountService {

    /**
     * IDとパスワードの組み合わせに対する認証処理を行う
     *
     * @param accountId : 管理者のアカウントID
     * @param password  : パスワード
     * @return 認証成功時は認証用トークンを生成して返却
     */
    String login(String accountId, String password);
}
