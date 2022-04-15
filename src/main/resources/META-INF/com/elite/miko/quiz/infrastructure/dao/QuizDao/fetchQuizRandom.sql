select
    tbl.quiz_id,
    question,
    commentary,
    status
from
    quiz as tbl,
    (select
         quiz_id
     from
         quiz
     where
         status = /* status */'1'
     order by
         random()
     limit
         /* count */10
    ) as random
where
    tbl.quiz_id = random.quiz_id
limit
    /* count */10