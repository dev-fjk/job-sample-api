-- password + saltでハッシュ化した文字列を登録
insert into
    admin_account (account_id, password)
values
    ('root', '7a37b85c8918eac19a9089c0fa5a2ab4dce3f90528dcdeec108b23ddf3607b99');