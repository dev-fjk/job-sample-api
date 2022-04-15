package com.elite.miko.quiz.infrastructure.model.entity;

import java.time.LocalDateTime;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Table;

/**
 * 管理者ユーザー用テーブル Entity
 */
@Data
@Entity
@Table(name = "admin_account")
public class AdminAccount {

    private String accountId;

    private String password;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
