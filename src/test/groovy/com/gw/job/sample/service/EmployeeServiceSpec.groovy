package com.gw.job.sample.service

import com.gw.job.sample.converter.EmployeeResponseConverter
import com.gw.job.sample.entity.doma.Employee
import com.gw.job.sample.entity.enums.DepartmentCode
import com.gw.job.sample.entity.request.EmployeeAddRequest
import com.gw.job.sample.entity.request.EmployeeUpdateRequest
import com.gw.job.sample.entity.response.EmployeeResponse
import com.gw.job.sample.entity.result.EmployeeListResult
import com.gw.job.sample.entity.selector.EmployeeListSelector
import com.gw.job.sample.exception.ResourceNotFoundException
import com.gw.job.sample.factory.EmployeeFactory
import com.gw.job.sample.repository.EmployeeRepository
import spock.lang.Specification

import java.time.LocalDate

class EmployeeServiceSpec extends Specification {

    EmployeeService target

    EmployeeRepository employeeRepository = Mock()
    EmployeeResponseConverter employeeResponseConverter = Mock()
    EmployeeFactory employeeFactory = Mock()

    def setup() {
        target = new EmployeeService(
                employeeRepository,
                employeeResponseConverter,
                employeeFactory
        )
    }

    def "findOne 社員情報を取得"() {
        given:
        def employeeId = 1L
        def employee = new Employee()
        def response = EmployeeResponse.builder().build()

        1 * employeeRepository.findOne(employeeId) >> Optional.of(employee)
        1 * employeeResponseConverter.convert(employee) >> response

        when:
        def actual = target.findOne(employeeId)

        then:
        actual == response
    }

    def "findOne 社員が見つからない場合例外が返る"() {
        given:
        def employeeId = 1L

        1 * employeeRepository.findOne(employeeId) >> Optional.empty()
        0 * employeeResponseConverter._

        when:
        target.findOne(employeeId)

        then:
        def ex = thrown(ResourceNotFoundException)
        ex.getMessage() == "社員情報が見つかりません"
    }

    def "findAll 社員一覧を取得"() {
        given:
        def selector = new EmployeeListSelector(
                start: 1,
                count: 20
        )

        and: "repositoryからの戻り値定義"
        def inEntryDate = LocalDate.of(2022, 7, 30)
        def dbEmployees = (1..2L).collect() {
            def entity = new Employee()
            entity.employeeId = it
            entity.lastName = "テスト"
            entity.firstName = "太郎"
            entity.entryDate = inEntryDate
            entity.departmentCode = DepartmentCode.SALES
            return entity
        }

        def employeeListResult = EmployeeListResult.builder()
                .total(100)
                .employees(dbEmployees)
                .build()

        1 * employeeRepository.search(selector) >> employeeListResult

        when:
        def actual = target.findAll(selector)

        then:
        verifyAll(actual) {
            total == 100
            start == 1
            count == 2
            employees.size() == 2
            verifyAll(employees[0]) {
                employeeId == 1L
                lastName == "テスト"
                firstName == "太郎"
                entryDate == inEntryDate
                departmentCode == DepartmentCode.SALES.getValue()
            }
            verifyAll(employees[1]) {
                employeeId == 2L
                lastName == "テスト"
                firstName == "太郎"
                entryDate == inEntryDate
                departmentCode == DepartmentCode.SALES.getValue()
            }
        }
    }

    def "findAll 社員情報が見つからない場合は社員情報は空のレスポンスが返る"() {
        given:
        def selector = new EmployeeListSelector(
                start: 1,
                count: 20
        )

        and: "repositoryからの戻り値定義"
        def employeeListResult = EmployeeListResult.builder()
                .total(100)
                .employees([])
                .build()

        1 * employeeRepository.search(selector) >> employeeListResult

        when:
        def actual = target.findAll(selector)

        then: "count: 0, 社員が空のレスポンスが返る"
        verifyAll(actual) {
            total == 100
            start == 1
            count == 0
            employees == []
        }
    }

    def "正常系 add 追加したリソースのIDが戻り値で返る"() {
        given:
        def request = new EmployeeAddRequest()
        def employee = new Employee()
        def addEntityId = 100L

        1 * employeeFactory.createAddEmployee(request) >> employee
        1 * employeeRepository.insert(employee) >> addEntityId

        when:
        def actual = target.add(request)

        then:
        actual == addEntityId
    }

    def "update 更新成功時はレスポンスが返る"() {
        given:
        def employeeId = 1L
        def request = new EmployeeUpdateRequest()
        def beforeEmployee = new Employee()
        def updateEmployee = new Employee()
        def response = EmployeeResponse.builder().build()

        1 * employeeRepository.findOneForUpdate(employeeId) >> Optional.of(beforeEmployee)
        1 * employeeFactory.createUpdateEmployee(employeeId, request) >> updateEmployee
        1 * employeeRepository.update(updateEmployee) >> updateEmployee
        1 * employeeResponseConverter.convert(updateEmployee) >> response

        when:
        def actual = target.update(employeeId, request)

        then:
        actual == response
    }

    def "update 更新対象が無い場合例外が返る"() {
        given:
        def employeeId = 1L

        1 * employeeRepository.findOneForUpdate(employeeId) >> Optional.empty()
        0 * employeeFactory._
        0 * employeeResponseConverter._

        when:
        target.update(employeeId, new EmployeeUpdateRequest())

        then:
        def ex = thrown(ResourceNotFoundException)
        ex.getMessage() == "社員情報が見つかりません"
    }

    def "delete 削除に成功"() {
        given:
        def employeeId = 1L

        when:
        target.delete(employeeId)

        then:
        1 * employeeRepository.deleteByEmployeeId(employeeId) >> true
    }

    def "delete 削除に失敗時は例外が返る"() {
        given:
        def employeeId = 1L
        1 * employeeRepository.deleteByEmployeeId(employeeId) >> false

        when:
        target.delete(employeeId)

        then:
        def ex = thrown(ResourceNotFoundException)
        ex.getMessage() == "社員情報が見つかりません ID: 1"
    }
}
