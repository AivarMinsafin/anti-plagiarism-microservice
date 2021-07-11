package ru.itis.javalab.plagiarism.app.utils;

import jplag.ExitException;

public class ComparingUtilException extends RuntimeException {

    public ComparingUtilException() {
        super();
    }

    public ComparingUtilException(String message) {
        super(message);
    }

    public ComparingUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComparingUtilException(Throwable cause) {
        super(cause);
    }

    protected ComparingUtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
