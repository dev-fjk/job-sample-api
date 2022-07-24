package com.gw.job.sample.factory;

import com.gw.job.sample.entity.doma.Employee;
import com.gw.job.sample.entity.enums.DepartmentCode;
import com.gw.job.sample.entity.request.EmployeeAddRequest;
import org.springframework.stereotype.Component;

/**
 * 社員作成を行うクラス
 */
@Component
public class EmployeeFactory {

    /**
     * 追加する社員情報を作成する
     *
     * @param addRequest 社員追加リクエスト
     * @return 追加する社員情報
     */
    public Employee createAddEmployee(EmployeeAddRequest addRequest) {
        final Employee employee = new Employee();
        employee.setLastName(addRequest.getLastName());
        employee.setFirstName(addRequest.getFirstName());
        employee.setEntryDate(addRequest.getEntryDate());
        employee.setDepartmentCode(DepartmentCode.of(addRequest.getDepartmentCode()));
        employee.setCreatedBy(addRequest.getCreatedBy());
        employee.setUpdatedBy(addRequest.getCreatedBy());
        return employee;
    }
}
