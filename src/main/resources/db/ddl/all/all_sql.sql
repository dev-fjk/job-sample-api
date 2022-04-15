drop table if exists admin_account;
create table admin_account
(
    account_id integer primary key,
    password   varchar(64)                            not null,
    created_at timestamp(3) default current_timestamp not null,
    updated_at timestamp(3) default current_timestamp not null
);
comment on column admin_account.account_id is '管理者用のログインID';
comment on column admin_account.password is 'ハッシュ化済みパスワード';
comment on column admin_account.created_at is 'レコード作成日';
comment on column admin_account.updated_at is 'レコード更新日';

drop table if exists quiz;
create table quiz
(
    quiz_id    integer generated always as identity primary key,
    question   varchar(200)                           not null,
    commentary varchar(200)                           not null,
    status     varchar(1)                             not null,
    created_at timestamp(3) default current_timestamp not null,
    updated_at timestamp(3) default current_timestamp not null
);

comment on column quiz.quiz_id is 'クイズID';
comment on column quiz.question is '問題文';
comment on column quiz.status is 'クイズステータス 1:有効, 2:リクエスト中, 9:無効';
comment on column quiz.status is 'クイズステータス';
comment on column quiz.created_at is 'レコード作成日';
comment on column quiz.updated_at is 'レコード更新日';

CREATE INDEX idx_quiz_quiz_id ON quiz (quiz_id);
CREATE INDEX idx_quiz_quiz_id_status ON quiz (quiz_id, status);

drop table if exists answer;
create table answer
(
    quiz_id        integer                                not null,
    answer_id      integer generated always as identity primary key,
    answer1        varchar(50)                            not null,
    answer2        varchar(50)                            not null,
    answer3        varchar(50)                            not null,
    answer4        varchar(50)                            not null,
    correct_number smallint                               not null,
    created_at     timestamp(3) default current_timestamp not null,
    updated_at     timestamp(3) default current_timestamp not null,
    foreign key (quiz_id) references quiz (quiz_id),
    unique (quiz_id)
);

comment on column answer.quiz_id is 'クイズID';
comment on column answer.answer_id is '回答ID';
comment on column answer.answer1 is '回答1';
comment on column answer.answer2 is '回答2';
comment on column answer.answer3 is '回答3';
comment on column answer.answer4 is '回答4';
comment on column answer.correct_number is '正解の回答番号';
comment on column answer.created_at is 'レコード作成日';
comment on column answer.updated_at is 'レコード更新日';

CREATE INDEX idx_answer_quiz_id ON answer (quiz_id);
CREATE INDEX idx_answer_quiz_id_answer_id ON answer (quiz_id, answer_id);