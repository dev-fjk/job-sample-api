package com.gw.job.sample.application.service;

import com.gw.job.sample.application.common.utility.HashUtil;
import com.gw.job.sample.application.common.utility.WebTokenUtil;
import com.gw.job.sample.application.exception.LoginFailureException;
import com.gw.job.sample.domain.repository.AdminAccountRepository;
import com.gw.job.sample.domain.service.AdminAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminAccountServiceImpl implements AdminAccountService {

    private static final String HEADER_BEARER = "Bearer";

    private final AdminAccountRepository adminAccountRepository;
    private final HashUtil hashUtil;
    private final WebTokenUtil webTokenUtil;

    /**
     * IDとパスワードの組み合わせに対する認証処理を行う
     *
     * @param accountId : 管理者のアカウントID
     * @param password  : パスワード
     * @return 認証成功時は認証用トークンを生成して返却
     */
    @Override
    public String login(String accountId, String password) {

        // パスワードをsha256でハッシュ化する
        final String hashedPassword = hashUtil.createSha256Password(password);
        final boolean isLogin = adminAccountRepository.login(accountId, hashedPassword);
        if (!isLogin) {
            throw new LoginFailureException("ログインに失敗しました");
        }

        // 認証に成功した場合はJson Web Token を生成
        // Bearer JWTToken値の形式で返却
        return HEADER_BEARER + " " + webTokenUtil.generateToken();
    }
}
