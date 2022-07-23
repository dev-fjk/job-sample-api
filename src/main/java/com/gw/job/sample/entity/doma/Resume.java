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
 * DB レジュメテーブル Entity
 */
@Data
@Entity
@Table(name = "resume")
public class Resume {

    /**
     * ユーザーID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    /**
     * 苗字
     */
    private String lastName;

    /**
     * 名前
     */
    private String firstName;

    /**
     * 苗字 フリガナ
     */
    private String lastNameKana;

    /**
     * 名前 フリガナ
     */
    private String firstNameKana;

    /**
     * 誕生日
     */
    private LocalDate birthDay;

    /**
     * 都道府県コード
     */
    private int prefectureCode;

    /**
     * 住所　番地・建物名
     */
    private String cityAddress;

    /**
     * 卒業した学校種別
     */
    private int schoolType;

    /**
     * 学校名
     */
    private String schoolName;

    /**
     * 就業経験
     */
    private boolean working;

    /**
     * 職務要約
     */
    private String jobDescription;

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
