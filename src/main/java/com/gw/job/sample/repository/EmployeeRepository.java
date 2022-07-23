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
}
