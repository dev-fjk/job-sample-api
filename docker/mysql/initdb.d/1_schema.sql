-- drop --
DROP TABLE IF EXISTS `task`;
DROP TABLE IF EXISTS `task_priority_master`;
DROP TABLE IF EXISTS `task_status_master`;
DROP TABLE IF EXISTS `task_user`;
DROP TABLE IF EXISTS `auth_user`;

-- create --
-- ユーザー認証テーブル
CREATE TABLE auth_user
(
    login_id   varchar(20) not null comment 'ログインID',
    password   varchar(64) not null comment 'ハッシュ化済みパスワード',
    created_at timestamp   not null default current_timestamp comment '作成日時',
    created_by varchar(30) not null comment '作成者',
    updated_at timestamp   not null default current_timestamp comment '更新日時',
    updated_by varchar(30) not null comment '更新者',
    PRIMARY KEY (login_id)
);

create index idx_auth_user_pass on auth_user (login_id, password);

-- ユーザー情報テーブル
CREATE TABLE task_user
(
    user_id    bigint      not null auto_increment comment 'ユーザーID',
    login_id   varchar(20) not null unique comment 'ログインID',
    name       varchar(30) not null comment 'ユーザー名',
    created_at timestamp   not null default current_timestamp comment '作成日時',
    created_by varchar(30) not null comment '作成者',
    updated_at timestamp   not null default current_timestamp comment '更新日時',
    updated_by varchar(30) not null comment '更新者',
    PRIMARY KEY (user_id),
    FOREIGN KEY (login_id) REFERENCES auth_user (login_id)
);

create index idx_task_user_auth on task_user (login_id);

-- ステータスマスタ
CREATE TABLE task_status_master
(
    status_id  INTEGER     not null comment 'ステータスID',
    status     varchar(20) not null comment 'ステータス',
    created_at timestamp   not null default current_timestamp comment '作成日時',
    created_by varchar(30) not null comment '作成者',
    updated_at timestamp   not null default current_timestamp comment '更新日時',
    updated_by varchar(30) not null comment '更新者',
    PRIMARY KEY (status_id)
);

create index idx_task_status_master_status on task_status_master (status_id, status);

-- 優先度マスタ
CREATE TABLE task_priority_master
(
    priority_id INTEGER     not null comment '優先度ID',
    priority    varchar(20) not null comment '優先度',
    created_at  timestamp   not null default current_timestamp comment '作成日時',
    created_by  varchar(30) not null comment '作成者',
    updated_at  timestamp   not null default current_timestamp comment '更新日時',
    updated_by  varchar(30) not null comment '更新者',
    PRIMARY KEY (priority_id)
);

create index idx_task_priority_master_priority on task_priority_master (priority_id, priority);

-- タスクテーブル
CREATE TABLE task
(
    task_id     bigint      not null auto_increment comment 'タスクID',
    user_id     bigint      not null comment 'ユーザーID',
    status_id   INTEGER     not null default 1 comment 'ステータスID',
    priority_id INTEGER     not null default 11 comment '優先度ID',
    start_date  date                 default null comment '開始日',
    end_date    date                 default null comment '終了日',
    term_date   date                 default null comment '期限日',
    created_at  timestamp   not null default current_timestamp comment '作成日時',
    created_by  varchar(30) not null comment '作成者',
    updated_at  timestamp   not null default current_timestamp comment '更新日時',
    updated_by  varchar(30) not null comment '更新者',
    PRIMARY KEY (task_id),
    FOREIGN KEY (user_id) REFERENCES task_user (user_id),
    FOREIGN KEY (status_id) REFERENCES task_status_master (status_id),
    FOREIGN KEY (priority_id) REFERENCES task_priority_master (priority_id)
);

create index idx_task_user_id on task (user_id);
create index idx_task_user_status_id on task (user_id, status_id);
create index idx_task_user_priority_id on task (user_id, priority_id);
create index idx_task_user_term on task (user_id, term_date);
