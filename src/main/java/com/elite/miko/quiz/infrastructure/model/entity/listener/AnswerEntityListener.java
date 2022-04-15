package com.elite.miko.quiz.infrastructure.model.entity.listener;

import com.elite.miko.quiz.infrastructure.model.entity.Answer;
import java.time.Clock;
import java.time.LocalDateTime;
import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PreUpdateContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnswerEntityListener implements EntityListener<Answer> {

    private Clock clock;

    @Autowired
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    /**
     * Updateの前の事前処理を行う
     *
     * @param answer  : 回答
     * @param context : context
     */
    @Override
    public void preUpdate(Answer answer, PreUpdateContext<Answer> context) {
        answer.setUpdatedAt(LocalDateTime.now(this.clock));
        EntityListener.super.preUpdate(answer, context);
    }
}
