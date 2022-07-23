-- 設計: https://namugahaku.atlassian.net/wiki/spaces/FUJII/pages/3211265/API+MYSQL

DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS posted_company;
DROP TABLE IF EXISTS resume_occupation;
DROP TABLE IF EXISTS resume_career;
DROP TABLE IF EXISTS resume;

CREATE TABLE resume
(
    user_id         BIGINT AUTO_INCREMENT NOT NULL COMMENT 'ユーザーID',
    last_name       VARCHAR(15)  NOT NULL COMMENT '苗字',
    first_name      VARCHAR(15)  NOT NULL COMMENT '名前',
    last_name_kana  VARCHAR(30)           DEFAULT NULL COMMENT '苗字のフリガナ',
    first_name_kana VARCHAR(30)           DEFAULT NULL COMMENT '名前のフリガナ',
    birthday        DATE         NOT NULL COMMENT '生年月日',
    prefecture_code TINYINT      NOT NULL COMMENT '都道府県コード 1 ~ 47',
    city_address    VARCHAR(50)  NOT NULL COMMENT '住所　番地・建物名',
    school_type     TINYINT      NOT NULL COMMENT '卒業した学校種別',
    school_name     VARCHAR(50)           DEFAULT NULL COMMENT '学校名',
    working         TINYINT(1)   NOT NULL COMMENT '就業経験 0:なし, 1:有り',
    job_description VARCHAR(300) NOT NULL COMMENT '職務要約',
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    created_by      VARCHAR(30)  NOT NULL DEFAULT '作成者',
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    updated_by      VARCHAR(30)  NOT NULL COMMENT '更新者',
    PRIMARY KEY (user_id)
);

CREATE TABLE resume_career
(
    user_id       BIGINT                NOT NULL COMMENT 'ユーザーID',
    experience_id BIGINT AUTO_INCREMENT NOT NULL COMMENT '在籍企業ID',
    company_name  VARCHAR(50)           NOT NULL COMMENT '企業名',
    belonging     TINYINT(1)            NOT NULL COMMENT '在籍中フラグ 0:退職済み, 1:在籍中',
    entry_date    DATE                  NOT NULL COMMENT '入社日',
    retired_date  DATE                           DEFAULT NULL COMMENT '退職日',
    created_at    TIMESTAMP             NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    created_by    VARCHAR(30)           NOT NULL DEFAULT '作成者',
    updated_at    TIMESTAMP             NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    updated_by    VARCHAR(30)           NOT NULL COMMENT '更新者',
    PRIMARY KEY (experience_id),
    FOREIGN KEY (user_id) REFERENCES resume (user_id)
);

CREATE INDEX idx_resume_career_user_id ON resume_career (user_id);

CREATE TABLE resume_occupation
(
    user_id       BIGINT                NOT NULL COMMENT 'ユーザーID',
    experience_id BIGINT                NOT NULL COMMENT '在籍企業ID',
    occupation_id BIGINT AUTO_INCREMENT NOT NULL COMMENT '経験職種ID',
    name          VARCHAR(50)           NOT NULL COMMENT '職種名',
    created_at    TIMESTAMP             NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    created_by    VARCHAR(30)           NOT NULL DEFAULT '作成者',
    updated_at    TIMESTAMP             NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    updated_by    VARCHAR(30)           NOT NULL COMMENT '更新者',
    PRIMARY KEY (occupation_id),
    FOREIGN KEY (user_id) REFERENCES resume (user_id),
    FOREIGN KEY (experience_id) REFERENCES resume_career (experience_id)
);

CREATE INDEX idx_resume_occupation_user_id ON resume_occupation (user_id);
CREATE INDEX idx_resume_occupation_experience_id ON resume_occupation (experience_id);

CREATE TABLE posted_company
(
    company_id BIGINT      NOT NULL COMMENT '企業ID',
    user_id    BIGINT      NOT NULL COMMENT 'ユーザーID',
    status     TINYINT     NOT NULL DEFAULT 1 COMMENT '選考状況',
    entry_date DATE                 DEFAULT NULL COMMENT '入社日',
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    created_by VARCHAR(30) NOT NULL DEFAULT '作成者',
    updated_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    updated_by VARCHAR(30) NOT NULL COMMENT '更新者',
    PRIMARY KEY (company_id, user_id)
);

CREATE INDEX idx_posted_company_status ON posted_company (status);

CREATE TABLE employee
(
    employee_id     BIGINT AUTO_INCREMENT NOT NULL COMMENT '従業員ID',
    last_name       VARCHAR(15)           NOT NULL COMMENT '苗字',
    first_name      VARCHAR(15)           NOT NULL COMMENT '名前',
    entry_date      DATE                  NOT NULL COMMENT '入社日',
    department_code TINYINT               NOT NULL COMMENT '部署コード',
    created_at      TIMESTAMP             NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    created_by      VARCHAR(30)           NOT NULL DEFAULT '作成者',
    updated_at      TIMESTAMP             NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    updated_by      VARCHAR(30)           NOT NULL COMMENT '更新者',
    PRIMARY KEY (employee_id)
);