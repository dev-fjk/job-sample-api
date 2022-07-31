package com.gw.job.sample.repository

import com.gw.job.sample.dao.EmployeeDao
import com.gw.job.sample.entity.doma.Employee
import com.gw.job.sample.entity.selector.EmployeeListSelector
import com.gw.job.sample.exception.RepositoryControlException
import org.seasar.doma.jdbc.SelectForUpdateType
import org.seasar.doma.jdbc.SelectOptions
import spock.lang.Specification
import spock.lang.Unroll

class EmployeeRepositorySpec extends Specification {

    EmployeeRepository target
    EmployeeDao employeeDao = Mock()

    def setup() {
        target = new EmployeeRepository(employeeDao)
    }

    def "findOne 社員情報が返る"() {
        given:
        def employeeId = 1L
        def employee = new Employee()

        when:
        def actual = target.findOne(employeeId)

        then:
        1 * employeeDao.findByEmployeeId(employeeId) >> employee
        actual == Optional.ofNullable(employee)
    }

    def "findOne 社員情報が見つからない場合空のOptionalが返る"() {
        given:
        def employeeId = 1L
        def employee = null

        1 * employeeDao.findByEmployeeId(employeeId) >> employee

        when:
        def actual = target.findOne(employeeId)

        then:
        actual == Optional.empty()
    }

    def "findOneForUpdate 社員情報の取得に成功"() {
        given:
        def employeeId = 1L
        def employee = new Employee()

        and: "for updateの設定が行われているSelectOptionsが渡されているか"
        1 * employeeDao.findWithOptions(employeeId, {
            verifyAll(it as SelectOptions) {
                it.forUpdateType == SelectForUpdateType.NORMAL
            }
        }) >> employee

        when:
        def actual = target.findOneForUpdate(employeeId)

        then:
        actual == Optional.of(employee)
    }

    def "findOneForUpdate 社員が見つからない場合空のOptionalが返る"() {
        given:
        def employeeId = 1L
        1 * employeeDao.findWithOptions(employeeId, {
            verifyAll(it as SelectOptions) {
                forUpdateType == SelectForUpdateType.NORMAL
            }
        }) >> null

        when:
        def actual = target.findOneForUpdate(employeeId)

        then:
        actual == Optional.empty()
    }

    @Unroll
    def "search 社員一覧を取得 #testName"() {
        given:
        def selector = new EmployeeListSelector(
                start: 1,
                count: 20
        )
        def inOffSet = 0

        and:
        def employees = [new Employee()] * employeeSize
        1 * employeeDao.search(selector, {
            verifyAll(it as SelectOptions) {
                offset == inOffSet
                limit == 20
            }
        }) >> employees

        when:
        def actual = target.search(selector)

        then:
        verifyAll(actual) {
            it.employees == employees
            getCount() == employeeSize
        }

        where:
        testName || employeeSize
        "1件取得"   || 1
        "2件取得"   || 2
    }

    def "insert"() {
        given:
        // UTでdomaの機能を使った自動採番までテストするのは少々厳しいので最初からIDを渡している
        def employee = new Employee(employeeId: 1L)

        when:
        def actual = target.insert(employee)

        then:
        actual == 1L
    }

    def "update 更新件数が1件"() {
        given:
        def employee = new Employee(employeeId: 100L)
        def updateCount = 1L

        1 * employeeDao.update(employee) >> updateCount
        1 * employeeDao.findByEmployeeId(100L) >> employee

        when:
        def actual = target.update(employee)

        then:
        actual == employee
    }

    @Unroll
    def "update 更新件数が1件以外の場合例外が返る #testName"() {
        given:
        def employee = new Employee(employeeId: 100L)
        def updateCount = inUpdateCount

        1 * employeeDao.update(employee) >> updateCount
        0 * employeeDao.findByEmployeeId(_)

        when:
        target.update(employee)

        then:
        def ex = thrown(RepositoryControlException)
        ex.getMessage() == "データの更新に失敗しました"

        where:
        testName | inUpdateCount
        "0件更新"   | 0
        "2件更新"   | 2
    }

    @Unroll
    def "deleteByEmployeeId #testName"() {
        given:
        def employeeId = 1L
        1 * employeeDao.deleteByEmployeeId(employeeId) >> deleteCount

        when:
        def actual = target.deleteByEmployeeId(employeeId)

        then:
        actual == expected

        where:
        testName     | deleteCount || expected
        "1件更新時は削除成功" | 1           || true
        "0件更新時は削除失敗" | 0           || false
        "2件更新時は削除失敗" | 2           || false
    }
}
