package com.gw.job.sample.converter;

import com.gw.job.sample.entity.doma.Employee;
import com.gw.job.sample.entity.response.EmployeeResponse;
import org.springframework.stereotype.Component;

/**
 * 社員レスポンスへ変換するconverter
 */
@Component
public class EmployeeResponseConverter {

    /**
     * 社員レスポンスを作成する
     *
     * @param employee 社員
     * @return {@link EmployeeResponse}
     */
    public EmployeeResponse convert(Employee employee) {
        return EmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .lastName(employee.getLastName())
                .firstName(employee.getFirstName())
                .entryDate(employee.getEntryDate())
                .departmentCode(employee.getDepartmentCode().getValue())
                .build();
    }
}
