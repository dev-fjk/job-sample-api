package com.gw.job.sample.entity.doma;

import java.time.LocalDateTime;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

/**
 * DB 経験職種テーブル Entity
 */
@Data
@Entity
@Table(name = "resume_occupation")
public class ResumeOccupation {

    /**
     * ユーザーID
     */
    private long userId;

    /**
     * 在籍企業ID
     */
    private long experienceId;

    /**
     * 経験職種ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long occupationId;

    /**
     * 経験職種名
     */
    private String name;

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
