package com.gw.job.sample.dao

/**
 * テスト用SQLのテンプレート
 */
class CapturedSqlTemplate {

    /**
     * employeeテーブルの全カラム指定(expand)
     */
    static final EMPLOYEE_ALL_COLUMN = """
              | SELECT
              | employee_id,
              | last_name,
              | first_name,
              | entry_date,
              | department_code,
              | created_at,
              | created_by,
              | updated_at,
              | updated_by
              """
}
