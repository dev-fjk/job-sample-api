package com.gw.job.sample.infrastructure.model.entity;

import com.gw.job.sample.domain.model.consts.QuizStatus;
import com.gw.job.sample.infrastructure.model.entity.listener.QuizEntityListener;
import java.time.LocalDateTime;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

/**
 * クイズテーブル Entity
 */
@Data
@Entity(listener = QuizEntityListener.class)
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizId;

    private String question;

    private String commentary;

    @Column(name = "status")
    private QuizStatus quizStatus;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
