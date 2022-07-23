package com.gw.job.sample.dao;

import com.gw.job.sample.entity.doma.Employee;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface EmployeeDao {

    /***
     * 社員情報を取得する
     * @param employeeId 社員ID
     * @return 社員情報
     */
    @Select
    Employee findByEmployeeId(long employeeId);
}
