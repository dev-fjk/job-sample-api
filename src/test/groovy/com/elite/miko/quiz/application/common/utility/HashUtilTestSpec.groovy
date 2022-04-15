package com.elite.miko.quiz.application.common.utility

import com.elite.miko.quiz.application.common.config.HashConfig
import org.apache.commons.codec.digest.DigestUtils
import spock.lang.Specification

class HashUtilTestSpec extends Specification {

    HashUtil target
    def hashConfig = Mock(HashConfig)

    def setup() {
        target = new HashUtil(hashConfig)
    }

    def "正常系_同じ値をハッシュ化すると毎回同じ結果になる"() {
        given:
        def password = "password"
        def salt = "salt"

        hashConfig.getSalt() >> salt
        def expected = DigestUtils.sha256Hex(password + salt)

        when:
        def actual = target.createSha256Password(password)

        then:
        actual == expected
    }
}
