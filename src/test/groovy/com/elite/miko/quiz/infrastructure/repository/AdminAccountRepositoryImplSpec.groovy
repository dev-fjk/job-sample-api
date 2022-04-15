package com.elite.miko.quiz.infrastructure.repository

import com.elite.miko.quiz.domain.repository.AdminAccountRepository
import com.elite.miko.quiz.infrastructure.dao.AdminAccountDao
import spock.lang.Specification
import spock.lang.Unroll

class AdminAccountRepositoryImplSpec extends Specification {

    AdminAccountRepository target
    def accountDao = Mock(AdminAccountDao)

    def setup() {
        target = new AdminAccountRepositoryImpl(accountDao)
    }

    @Unroll
    def "正常系_login_#testName"() {
        given:
        def accountId = "id"
        def password = "password"
        when:
        def actual = target.login(accountId, password)

        then:
        1 * accountDao.countByAccountIdAndPassword(accountId, password) >> accountCount
        actual == expected

        where:
        testName      | accountCount || expected
        "アカウントの件数が1件" | 1            || true
        "アカウントの件数が0件" | 0            || false
        "アカウントの件数が2件" | 2            || false
    }

    @Unroll
    def "異常系_login_引数がNull_#testName"() {
        when:
        target.login(accountId, password)

        then:
        thrown(NullPointerException)
        0 * accountDao.countByAccountIdAndPassword(*_)

        where:
        testName         | accountId   | password
        "accountIdがnull" | null        | "password"
        "passwordがnull"  | "accountId" | null
        "全てnull"         | null        | null
    }
}
