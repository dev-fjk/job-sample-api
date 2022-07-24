package com.gw.job.sample.repository;

import com.gw.job.sample.dao.EmployeeDao;
import com.gw.job.sample.entity.doma.Employee;
import com.gw.job.sample.entity.result.EmployeeListResult;
import com.gw.job.sample.entity.selector.EmployeeListSelector;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.seasar.doma.jdbc.SelectOptions;
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
     * 社員一覧を取得する
     *
     * @param selector 検索条件
     * @return {@link EmployeeListResult}
     */
    public EmployeeListResult search(EmployeeListSelector selector) {
        // 取得開始位置は 1以上の値を貰っているので DBに検索する前に-1する
        var offset = selector.getStart() - 1;
        var options = SelectOptions.get().offset(offset).limit(selector.getCount()).count();
        var employees = employeeDao.search(selector, options);
        return EmployeeListResult.builder()
                .total(options.getCount())
                .employees(employees)
                .build();
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

    /**
     * 社員を削除する
     *
     * @param employeeId 削除する社員の社員ID
     * @return 削除に成功した場合true
     */
    public boolean deleteByEmployeeId(long employeeId) {
        int deleteCount = employeeDao.deleteByEmployeeId(employeeId);
        return deleteCount == 1;
    }
}
