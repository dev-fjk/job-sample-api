package com.gw.job.sample.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

/**
 * レジュメテーブル Entity
 */
@Data
@Entity
@Table(name = "resume")
public class Resume {

    @Id
    private long userId;

    private String lastName;

    private String firstName;

    private LocalDate birthDate;

    private String jobDescription;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdAt;

    private String createdBy;

    @Column(insertable = false)
    private LocalDateTime updatedAt;

    private String updatedBy;
}
