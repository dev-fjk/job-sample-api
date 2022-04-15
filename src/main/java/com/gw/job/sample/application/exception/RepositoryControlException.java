package com.gw.job.sample.application.exception;

/**
 * リポジトリ操作時のエラー
 */
public class RepositoryControlException extends RuntimeException {

    private static final long serialVersionUID = 1050198995395212276L;

    public RepositoryControlException(String message) {
        super(message);
    }
}
