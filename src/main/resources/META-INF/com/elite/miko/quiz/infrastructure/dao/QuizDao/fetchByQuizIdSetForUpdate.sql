select
    quiz_id,
    question,
    commentary,
    status
from
    quiz
where
    quiz_id in /* quizIdSet */(1, 2)
for update