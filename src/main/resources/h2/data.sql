-- 企業マスタ
insert into mst_company (cid, company_name) values (1, 'green ways');
insert into mst_company (cid, company_name) values (2, 'wonder tree');

-- ユーザーの履歴書
insert into resume (user_id, last_name, first_name, birth_date, job_description, created_by, updated_by) values (1, 'test', 'taro', '1990-10-20', 'example', 'manual', 'manual');
insert into resume (user_id, last_name, first_name, birth_date, job_description, created_by, updated_by) values (2, 'sample', 'jiro', '1990-10-20', 'programmer', 'manual', 'manual');

-- 応募企業
insert into posted_company(user_id, cid, created_by, updated_by) values (1, 1, 'manual', 'manual');