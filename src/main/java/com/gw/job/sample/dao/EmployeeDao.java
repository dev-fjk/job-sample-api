package com.gw.job.sample.dao;

import com.gw.job.sample.entity.doma.Employee;
import com.gw.job.sample.entity.selector.EmployeeListSelector;
import java.util.List;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

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

    /**
     * 従業員一覧を取得する
     *
     * @param selector 検索条件
     * @param options  doma 検索オプション
     * @return {@link Employee} のリスト データが引けない場合は空リストを返却
     */
    @Select
    List<Employee> search(EmployeeListSelector selector, SelectOptions options);

    /**
     * 社員を追加する
     *
     * @param employee 追加する社員情報
     * @return 追加件数
     */
    @Insert(excludeNull = true)
    int insert(Employee employee);

    /**
     * 社員情報を削除する
     *
     * @param employeeId 社員ID
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteByEmployeeId(long employeeId);
}
