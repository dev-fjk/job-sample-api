-- drop --
DROP TABLE IF EXISTS posted_company;
DROP TABLE IF EXISTS mst_company;
DROP TABLE IF EXISTS resume;

-- レジュメテーブル ユーザーの職務経歴書情報
CREATE TABLE resume
(
    user_id         bigint      not null comment 'ユーザーID',
    last_name       varchar(30) not null comment '苗字',
    first_name      varchar(30) not null comment '氏名',
    birth_date      date        not null comment '生年月日',
    job_description varchar(400)         default null comment '職務要約',
    created_at      timestamp   not null default current_timestamp comment '作成日時',
    created_by      varchar(30) not null comment '作成者',
    updated_at      timestamp   not null default current_timestamp comment '更新日時',
    updated_by      varchar(30) not null comment '更新者',
    PRIMARY KEY (user_id)
);

-- 企業マスタテーブル
CREATE TABLE mst_company
(
    cid          bigint      not null comment '企業ID',
    company_name varchar(30) not null comment '企業名',
    PRIMARY KEY (cid)
);

-- 応募企業テーブル
CREATE TABLE posted_company
(
    user_id     bigint      not null comment 'ユーザーID',
    cid         bigint      not null comment '企業ID',
    posted_date timestamp   not null default current_timestamp comment '応募日時',
    selection_status tinyint(1)  not null default 0 comment '選考状況 0: 選考前, 1: 選考中, 2: 採用, 9: 不採用',
    created_at  timestamp   not null default current_timestamp comment '作成日時',
    created_by  varchar(30) not null comment '作成者',
    updated_at  timestamp   not null default current_timestamp comment '更新日時',
    updated_by  varchar(30) not null comment '更新者',
    PRIMARY KEY (user_id, cid),
    FOREIGN KEY (user_id) REFERENCES resume (user_id),
    FOREIGN KEY (cid) REFERENCES mst_company (cid)
);

create index idx_posted_company_selection_status on posted_company(selection_status);