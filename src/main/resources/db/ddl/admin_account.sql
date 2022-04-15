drop table if exists admin_account;
create table admin_account
(
    account_id varchar(12) primary key,
    password   varchar(64)                            not null,
    created_at timestamp(3) default current_timestamp not null,
    updated_at timestamp(3) default current_timestamp not null
);
comment on column admin_account.account_id is '管理者用のログインID';
comment on column admin_account.password is 'ハッシュ化済みパスワード';
comment on column admin_account.created_at is 'レコード作成日';
comment on column admin_account.updated_at is 'レコード更新日';
