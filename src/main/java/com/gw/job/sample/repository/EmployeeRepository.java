package com.gw.job.sample.repository;

import com.gw.job.sample.dao.EmployeeDao;
import com.gw.job.sample.entity.doma.Employee;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 社員テーブル Repository
 */
@Repository
@RequiredArgsConstructor
public class EmployeeRepository {

    private final EmployeeDao employeeDao;

    /**
     * 社員情報を取得する
     *
     * @param employeeId 社員ID
     * @return {@link Employee} 社員情報
     */
    public Optional<Employee> findOne(long employeeId) {
        var employee = employeeDao.findByEmployeeId(employeeId);
        return Optional.ofNullable(employee);
    }

    /**
     * 社員を追加する
     *
     * @param employee 登録する社員情報
     * @return 登録した社員情報のID(Domaでinsertした場合 insertに使用したentityに 自動採番されたIDが設定される)
     */
    public long insert(Employee employee) {
        employeeDao.insert(employee);
        return employee.getEmployeeId();
    }
}
