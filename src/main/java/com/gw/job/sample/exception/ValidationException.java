package com.gw.job.sample.exception;

import java.util.Collections;
import java.util.List;

/**
 * 自作バリデーションエラー時のException
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 8319097489973554483L;

    public ValidationException(String message) {
        this(Collections.singletonList(message));
    }

    public ValidationException(List<String> messageList) {
        super(String.join(",", messageList));
    }
}
