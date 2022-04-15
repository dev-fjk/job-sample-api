package com.gw.job.sample.application.exception;

/**
 * ログイン失敗時のエラー
 */
public class LoginFailureException extends RuntimeException {

    private static final long serialVersionUID = -978523585432769297L;

    public LoginFailureException(String message) {
        super(message);
    }
}
