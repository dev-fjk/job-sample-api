package com.gw.job.sample.entity.result;

import com.gw.job.sample.entity.doma.Employee;
import java.util.List;
import lombok.Builder;
import lombok.Value;

/**
 * 社員一覧取得結果
 */
@Value
@Builder
public class EmployeeListResult {

    /**
     * 総件数
     */
    long total;

    /**
     * 取得した社員情報
     */
    List<Employee> employees;

    /**
     * 取得件数(count) を取得する
     *
     * @return DBから取得できたデータの件数
     */
    public int getCount() {
        return this.employees.size();
    }
}
