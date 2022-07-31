package com.gw.job.sample.factory

import com.gw.job.sample.entity.enums.DepartmentCode
import com.gw.job.sample.entity.request.EmployeeAddRequest
import com.gw.job.sample.entity.request.EmployeeUpdateRequest
import spock.lang.Specification

import java.time.LocalDate

class EmployeeFactorySpec extends Specification {

    EmployeeFactory target

    def setup() {
        target = new EmployeeFactory()
    }

    def "createAddEmployee"() {
        given:
        def expectedEntryDate = LocalDate.of(2022, 7, 30)
        def expectedDepartmentCode = DepartmentCode.SALES
        def request = new EmployeeAddRequest(
                lastName: "テスト",
                firstName: "太郎",
                entryDate: expectedEntryDate,
                departmentCode: expectedDepartmentCode.getValue(),
                createdBy: "test"
        )

        when:
        def actual = target.createAddEmployee(request)

        then:
        verifyAll(actual) {
            lastName == "テスト"
            firstName == "太郎"
            entryDate == expectedEntryDate
            departmentCode == expectedDepartmentCode
            createdBy == "test"
            updatedBy == "test"
        }
    }

    def "createUpdateEmployee"() {
        given:
        def employeeId = 1L
        def expectedDepartmentCode = DepartmentCode.SALES
        def request = new EmployeeUpdateRequest(
                lastName: "テスト",
                firstName: "太郎",
                departmentCode: expectedDepartmentCode.getValue(),
                updatedBy: "test"
        )

        when:
        def actual = target.createUpdateEmployee(employeeId, request)

        then:
        verifyAll(actual) {
            it.employeeId == employeeId
            lastName == "テスト"
            firstName == "太郎"
            entryDate == null
            departmentCode == expectedDepartmentCode
            updatedBy == "test"
        }
    }
}
