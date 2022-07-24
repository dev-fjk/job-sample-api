package com.gw.job.sample.service;

import com.gw.job.sample.entity.request.EmployeeAddRequest;
import com.gw.job.sample.entity.response.EmployeeListResponse;
import com.gw.job.sample.entity.response.EmployeeResponse;
import com.gw.job.sample.entity.selector.EmployeeListSelector;
import com.gw.job.sample.exception.ResourceNotFoundException;
import com.gw.job.sample.factory.EmployeeFactory;
import com.gw.job.sample.repository.EmployeeRepository;
import java.util.List;
import java.util.stream.Collectors;
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
     * 従業員一覧を取得しレスポンスを作成する
     *
     * @param selector 検索条件
     * @return {@link EmployeeListResponse}
     */
    public EmployeeListResponse findAll(EmployeeListSelector selector) {

        var searchResult = employeeRepository.search(selector);
        if (searchResult.getCount() == 0) {
            // データが取得出来なかった場合は空レスポンスを作成して返す
            return EmployeeListResponse.empty(searchResult.getTotal(), selector.getStart());
        }

        // レスポンスに設定する従業員情報へ変換する
        List<EmployeeListResponse.Employee> employees = searchResult.getEmployees().stream()
                .map(emp -> EmployeeListResponse.Employee.builder()
                        .employeeId(emp.getEmployeeId())
                        .lastName(emp.getLastName())
                        .firstName(emp.getFirstName())
                        .entryDate(emp.getEntryDate())
                        .departmentCode(emp.getDepartmentCode().getValue())
                        .build())
                .collect(Collectors.toList());

        return EmployeeListResponse.builder()
                .total(searchResult.getTotal())
                .start(selector.getStart())
                .count(searchResult.getCount())
                .employees(employees)
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

    /**
     * 社員を削除する
     *
     * @param employeeId 社員ID
     * @throws ResourceNotFoundException 削除対象が見つからない場合throw
     */
    @Transactional(rollbackFor = Throwable.class)
    public void delete(long employeeId) {
        boolean isDelete = employeeRepository.deleteByEmployeeId(employeeId);
        if (!isDelete) {
            throw new ResourceNotFoundException("従業員情報が見つかりません ID:" + employeeId);
        }
    }
}
