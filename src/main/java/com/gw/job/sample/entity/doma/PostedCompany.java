package com.gw.job.sample.entity.doma;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import com.gw.job.sample.entity.enums.PostedCompanyStatus;

/**
 * DB 応募企業テーブル Entity
 */
@Data
@Entity
@Table(name = "posted_company")
public class PostedCompany {

    /**
     * 企業ID
     */
    @Id
    private long companyId;

    /**
     * ユーザーID
     */
    @Id
    private long userId;

    /**
     * 選考状況
     */
    private PostedCompanyStatus status;

    /**
     * 入社日
     */
    private LocalDate entryDate;

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
