package com.gw.job.sample.converter

import com.gw.job.sample.entity.doma.Employee
import com.gw.job.sample.entity.enums.DepartmentCode
import spock.lang.Specification

import java.time.LocalDate

class EmployeeResponseConverterSpec extends Specification {

    EmployeeResponseConverter target

    def setup() {
        target = new EmployeeResponseConverter()
    }

    def "convert"() {
        given:
        def inDepartmentCode = DepartmentCode.AFFAIRS
        def expectedEntryDate = LocalDate.of(2022, 7, 30)
        def employee = new Employee(
                employeeId: 1L,
                lastName: "テスト",
                firstName: "太郎",
                entryDate: expectedEntryDate,
                departmentCode: inDepartmentCode
        )

        when:
        def actual = target.convert(employee)

        then:
        verifyAll(actual) {
            employeeId == 1L
            lastName == "テスト"
            firstName == "太郎"
            entryDate == expectedEntryDate
            departmentCode == inDepartmentCode.getValue()
        }
    }
}
