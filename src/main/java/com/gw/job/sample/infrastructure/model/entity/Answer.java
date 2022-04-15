package com.gw.job.sample.infrastructure.model.entity;

import com.gw.job.sample.infrastructure.model.entity.listener.AnswerEntityListener;
import java.time.LocalDateTime;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

/**
 * Answerテーブル Entity
 */
@Data
@Entity(listener = AnswerEntityListener.class)
@Table(name = "answer")
public class Answer {

    private Long quizId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    private String answer1;

    private String answer2;

    private String answer3;

    private String answer4;

    private int correctNumber;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
