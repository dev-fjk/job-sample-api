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