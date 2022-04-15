package com.gw.job.sample.application.exception;

/**
 * クイズの取得件数が足りない場合のエラー
 */
public class QuizNotEnoughCountException extends RuntimeException {

    private static final long serialVersionUID = -6018839821328345041L;

    public QuizNotEnoughCountException(String message) {
        super(message);
    }
}
