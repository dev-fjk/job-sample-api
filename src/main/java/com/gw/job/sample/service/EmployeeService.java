package com.gw.job.sample.service;

import com.gw.job.sample.entity.response.EmployeeResponse;
import com.gw.job.sample.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.gw.job.sample.repository.EmployeeRepository;

/**
 * 社員情報　サービス
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * 社員情報を取得する
     *
     * @param employeeId 　社員ID
     * @return {@link EmployeeResponse}
     *
     * @throws ResourceNotFoundException 社員情報が見つからない場合
     */
    public EmployeeResponse findOne(long employeeId) {

        var employee = employeeRepository.findOne(employeeId)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("社員情報が見つかりません");
                });

        return EmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .lastName(employee.getLastName())
                .firstName(employee.getFirstName())
                .entryDate(employee.getEntryDate())
                .departmentCode(employee.getDepartmentCode())
                .build();
    }
}
