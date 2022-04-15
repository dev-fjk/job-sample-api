-- 認証テーブル
INSERT INTO
    auth_user(login_id, password, created_by, updated_by)
VALUES
    ('userId1', 'password', 'testUser', 'test_user');
INSERT INTO
    auth_user(login_id, password, created_by, updated_by)
VALUES
    ('userId2', 'password', 'testUser', 'test_user');
-- ユーザー
INSERT INTO
    task_user(login_id, name, created_by, updated_by)
VALUES
    ('userId1', 'dev-fjk', 'testUser', 'test_user');
INSERT INTO
    task_user(login_id, name, created_by, updated_by)
VALUES
    ('userId2', '田中', 'testUser', 'test_user');
-- ステータス マスタ
INSERT INTO
    task_status_master(status_id, status, created_by, updated_by)
VALUES
    (1, 'TODO', 'admin', 'admin');
INSERT INTO
    task_status_master(status_id, status, created_by, updated_by)
VALUES
    (2, 'PROGRESS', 'admin', 'admin');
INSERT INTO
    task_status_master(status_id, status, created_by, updated_by)
VALUES
    (3, 'WAIT', 'admin', 'admin');
INSERT INTO
    task_status_master(status_id, status, created_by, updated_by)
VALUES
    (4, 'RESOLVED', 'admin', 'admin');

-- 優先度 マスタ
INSERT INTO
    task_priority_master(priority_id, priority, created_by, updated_by)
VALUES
    (11, 'LOW', 'admin', 'admin');
INSERT INTO
    task_priority_master(priority_id, priority, created_by, updated_by)
VALUES
    (12, 'MIDDLE', 'admin', 'admin');
INSERT INTO
    task_priority_master(priority_id, priority, created_by, updated_by)
VALUES
    (13, 'HIGH', 'admin', 'admin');

INSERT INTO
    task(user_id, status_id, priority_id, start_date, end_date, term_date, created_by, updated_by)
VALUES
    (1, 1, 11, null, null, '2022-02-22', '1', '1');
INSERT INTO
    task(user_id, status_id, priority_id, start_date, end_date, term_date, created_by, updated_by)
VALUES
    (1, 2, 12, '2022-02-15', null, '2022-02-25', '1', '1');
INSERT INTO
    task(user_id, status_id, priority_id, start_date, end_date, term_date, created_by, updated_by)
VALUES
    (1, 3, 11, '2022-02-20', null, '2022-02-26', '1', '1');
INSERT INTO
    task(user_id, status_id, priority_id, start_date, end_date, term_date, created_by, updated_by)
VALUES
    (1, 4, 13, '2022-03-01', '2022-03-10', '2022-03-10', '1', '1');
INSERT INTO
    task(user_id, status_id, priority_id, start_date, end_date, term_date, created_by, updated_by)
VALUES
    (2, 1, 11, null, null, '2022-02-25', '2', '2');
INSERT INTO
    task(user_id, status_id, priority_id, start_date, end_date, term_date, created_by, updated_by)
VALUES
    (2, 1, 13, null, null, '2022-02-27', '2', '2');

