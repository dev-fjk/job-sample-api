package com.gw.job.sample.repository

import com.gw.job.sample.dao.EmployeeDao
import com.gw.job.sample.entity.doma.Employee
import spock.lang.Specification

class EmployeeRepositoryTest extends Specification {

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
}
