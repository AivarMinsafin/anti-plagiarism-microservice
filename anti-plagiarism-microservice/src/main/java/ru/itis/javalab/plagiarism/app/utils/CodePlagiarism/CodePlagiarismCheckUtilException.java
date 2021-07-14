package ru.itis.javalab.plagiarism.app.utils.CodePlagiarism;

import jplag.ExitException;

public class CodePlagiarismCheckUtilException extends RuntimeException {

    public CodePlagiarismCheckUtilException() {
        super();
    }

    public CodePlagiarismCheckUtilException(String message) {
        super(message);
    }

    public CodePlagiarismCheckUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodePlagiarismCheckUtilException(Throwable cause) {
        super(cause);
    }

    protected CodePlagiarismCheckUtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
