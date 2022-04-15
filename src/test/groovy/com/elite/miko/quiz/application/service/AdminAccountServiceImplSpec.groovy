package com.elite.miko.quiz.application.service

import com.elite.miko.quiz.application.common.utility.HashUtil
import com.elite.miko.quiz.application.common.utility.WebTokenUtil
import com.elite.miko.quiz.application.exception.LoginFailureException
import com.elite.miko.quiz.domain.repository.AdminAccountRepository
import com.elite.miko.quiz.domain.service.AdminAccountService
import spock.lang.Specification

class AdminAccountServiceImplSpec extends Specification {

    AdminAccountService target

    def adminAccountRepository = Mock(AdminAccountRepository)
    def hashUtil = Mock(HashUtil)
    def webTokenUtil = Mock(WebTokenUtil)

    def setup() {
        target = new AdminAccountServiceImpl(adminAccountRepository, hashUtil, webTokenUtil)
    }

    def "正常系_login_ログインに成功"() {
        given:
        def accountId = "account"
        def password = "password"

        def hashedPass = "sha256Pass"
        def jsonWebToken = "token"

        when:
        def actual = target.login(accountId, password)

        then:
        1 * hashUtil.createSha256Password(password) >> hashedPass
        1 * adminAccountRepository.login(accountId, hashedPass) >> true
        1 * webTokenUtil.generateToken() >> jsonWebToken

        // Bearer JWTの形式で返却されているか
        actual == "Bearer" + " " + jsonWebToken
    }

    def "異常系_ログインに失敗"() {
        given:
        def accountId = "account"
        def password = "password"

        def hashedPass = "sha256Pass"

        1 * hashUtil.createSha256Password(password) >> hashedPass
        1 * adminAccountRepository.login(accountId, hashedPass) >> false
        0 * webTokenUtil.generateToken()

        when:
        target.login(accountId, password)

        then:
        def exception = thrown(LoginFailureException)
        exception.getMessage() == "ログインに失敗しました"
    }
}
