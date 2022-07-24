SELECT /*%expand*/*
FROM
    employee
WHERE
/*%if selector.employeeIds.size() >= 1 */
      employee_id IN /*selector.employeeIds*/(1)
/*%end*/
/*%if selector.departmentCodes.size() >= 1 */
  AND department_code IN /*selector.departmentCodes*/(1)
/*%end*/
/*%if selector.entryDateFrom != null */
  AND entry_date >= /*selector.entryDateFrom*/'2022-01-01'
/*%end*/
/*%if selector.entryDateTo != null */
  AND entry_date <= /*selector.entryDateTo*/'2022-01-31'
/*%end*/
ORDER BY
    employee_id