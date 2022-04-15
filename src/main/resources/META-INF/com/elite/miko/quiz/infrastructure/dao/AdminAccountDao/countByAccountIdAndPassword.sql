select
    count(*)
from
    admin_account
where
    account_id = /* accountId */1
  and
    password = /* password */'password'
