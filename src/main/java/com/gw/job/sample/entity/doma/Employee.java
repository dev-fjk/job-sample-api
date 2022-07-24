package com.gw.job.sample.entity.doma;

import com.gw.job.sample.entity.enums.DepartmentCode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

/**
 * DB 従業員テーブル Entity
 */
@Data
@Entity
@Table(name = "employee")
public class Employee {

    /**
     * 企業ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    /**
     * 苗字
     */
    private String lastName;

    /**
     * 名前
     */
    private String firstName;

    /**
     * 入社日
     */
    private LocalDate entryDate;

    /**
     * 部署コード
     */
    private DepartmentCode departmentCode;

    /**
     * 作成日時
     */
    @Column(insertable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 作成者
     */
    @Column(updatable = false)
    private String createdBy;

    /**
     * 更新日時
     */
    @Column(insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    /**
     * 更新者
     */
    private String updatedBy;
}
