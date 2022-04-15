select
    quiz_id,
    answer_id,
    answer1,
    answer2,
    answer3,
    answer4,
    correct_number
from
    answer
where
    quiz_id in /* quizIdList */(1, 2)
