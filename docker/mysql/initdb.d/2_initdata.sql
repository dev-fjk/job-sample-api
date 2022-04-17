-- 企業マスタ
insert into mst_company (cid, company_name) values (1, '株式会社 グリーンウェイズ');
insert into mst_company (cid, company_name) values (2, '株式会社 ワンダーツリー');

-- ユーザーの履歴書
insert into resume (user_id, last_name, first_name, birth_date, job_description, created_by, updated_by) values (1, 'テスト', '太郎', '1990-10-20', '営業を経験しました', 'manual', 'manual');
insert into resume (user_id, last_name, first_name, birth_date, job_description, created_by, updated_by) values (2, 'サンプル', '次郎', '1990-10-20', 'プログラマーを経験しました', 'manual', 'manual');

-- 応募企業
insert into posted_company(user_id, cid, created_by, updated_by) values (1, 1, 'manual', 'manual');
