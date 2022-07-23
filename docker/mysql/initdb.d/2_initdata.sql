INSERT INTO
    resume (user_id, last_name, first_name, last_name_kana, first_name_kana, birthday, prefecture_code, city_address,
            school_type, school_name, working, job_description, created_by, updated_by)
VALUES
    (1,
     'テスト',
     '太郎',
     'テスト',
     'タロウ',
     '2000-02-10',
     '14',
     '横浜市神奈川区XX町1町目1番地',
     '2',
     '神奈川大学',
     '1',
     'テスト要約',
     'manual',
     'manual')
  , (2,
     '田中',
     '次郎',
     'タナカ',
     'ジロウ',
     '2005-8-31',
     '14',
     '横浜市港北区XX町1町目1番地',
     '2',
     'テスト大学',
     '0',
     'テスト要約',
     'manual',
     'manual');

INSERT INTO
    resume_career (user_id, experience_id, company_name, belonging, entry_date, retired_date, created_by, updated_by)
VALUES
    (1,
     1,
     '株式会社離職済み',
     0,
     '2015-04-01',
     '2018-07-10',
     'manual',
     'manual')
  , (1,
     2,
     '株式会社在籍中',
     1,
     '2015-04-01',
     null,
     'manual',
     'manual');

INSERT INTO
    resume_occupation (user_id, experience_id, occupation_id, name, created_by, updated_by)
VALUES
    (1,
     1,
     1,
     '営業',
     'manual',
     'manual')
  , (1,
     1,
     2,
     'テレアポ',
     'manual',
     'manual')
  , (1,
     2,
     3,
     'エンジニア',
     'manual',
     'manual');

INSERT INTO
    posted_company (company_id, user_id, status, entry_date, created_by, updated_by)
VALUES
    (1,
     1,
     1,
     null,
     'manual',
     'manual')
  , (2,
     1,
     3,
     '2022-08-01',
     'manual',
     'manual')
  , (1,
     2,
     2,
     null,
     'manual',
     'manual');

INSERT INTO
    employee (employee_id, last_name, first_name, entry_date, department_code, created_by, updated_by)
VALUES
    (1,
     '社長',
     '太郎',
     '2022-04-01',
     1,
     'manual',
     'manual')
  , (2,
     '営業',
     '次郎',
     '2022-06-01',
     4,
     'manual',
     'manual')
  , (3,
     '開発',
     '一号',
     '2022-05-01',
     5,
     'manual',
     'manual')
  , (4,
     '開発',
     '二号',
     '2022-05-30',
     5,
     'manual',
     'manual');