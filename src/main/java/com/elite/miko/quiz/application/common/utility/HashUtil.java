package com.elite.miko.quiz.application.common.utility;

import com.elite.miko.quiz.application.common.config.HashConfig;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HashUtil {

    private final HashConfig hashConfig;

    /**
     * 引数のパスワードと環境別の固定値を連結した文字列を sha256でハッシュ化する
     *
     * @return sha256形式でハッシュ化されたパスワード
     */
    public String createSha256Password(final String password) {
        return DigestUtils.sha256Hex(password + hashConfig.getSalt());
    }
}
