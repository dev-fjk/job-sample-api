package com.gw.job.sample.exception;

/**
 * リソースが既に存在する場合のエラー
 */
public class ResourceAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = -2128348422937977468L;

    public ResourceAlreadyExistException(String message) {
        super(message);
    }
}
