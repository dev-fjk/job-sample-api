package com.gw.job.sample.entity.doma;

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
 * DB 在籍企業テーブル Entity
 */
@Data
@Entity
@Table(name = "resume_career")
public class ResumeCareer {

    /**
     * ユーザーID
     */
    private long userId;

    /**
     * 在籍企業ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long experienceId;

    /**
     * 企業名
     */
    private String companyName;

    /**
     * 在籍中フラグ
     */
    private boolean belonging;

    /**
     * 入社日
     */
    private LocalDate entryDate;

    /**
     * 退職日
     */
    private LocalDate retiredDate;

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
