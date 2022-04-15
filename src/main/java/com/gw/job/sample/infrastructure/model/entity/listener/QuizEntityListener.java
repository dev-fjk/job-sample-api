package com.gw.job.sample.infrastructure.model.entity.listener;

import com.gw.job.sample.infrastructure.model.entity.Quiz;
import java.time.Clock;
import java.time.LocalDateTime;
import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PreUpdateContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuizEntityListener implements EntityListener<Quiz> {

    private Clock clock;

    @Autowired
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    /**
     * Updateの前の事前処理を行う
     *
     * @param quiz    : クイズ
     * @param context : context
     */
    @Override
    public void preUpdate(Quiz quiz, PreUpdateContext<Quiz> context) {
        quiz.setUpdatedAt(LocalDateTime.now(this.clock));
        EntityListener.super.preUpdate(quiz, context);
    }
}
