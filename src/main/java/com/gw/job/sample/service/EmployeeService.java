package com.gw.job.sample.service;

import com.gw.job.sample.entity.request.EmployeeAddRequest;
import com.gw.job.sample.entity.response.EmployeeResponse;
import com.gw.job.sample.exception.ResourceNotFoundException;
import com.gw.job.sample.factory.EmployeeFactory;
import com.gw.job.sample.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 社員情報　サービス
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeFactory employeeFactory;

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
                .departmentCode(employee.getDepartmentCode().getValue())
                .build();
    }

    /**
     * 社員を追加する
     * 追加成功時は作成した社員のIDを返す
     *
     * @param addRequest 社員追加リクエスト
     * @return 作成した社員のID
     */
    @Transactional(rollbackFor = Throwable.class)
    public long add(EmployeeAddRequest addRequest) {
        var addEmployee = employeeFactory.createAddEmployee(addRequest);
        return employeeRepository.insert(addEmployee);
    }
}
