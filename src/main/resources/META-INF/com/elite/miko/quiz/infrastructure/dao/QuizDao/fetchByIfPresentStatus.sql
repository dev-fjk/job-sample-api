select
    quiz_id,
    question,
    commentary,
    status
from
    quiz
where
/*%if quizStatus != null */
    status = /* quizStatus */'request'
/*%end*/
order by
    quiz_id