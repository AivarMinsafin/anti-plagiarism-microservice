package ru.itis.javalab.plagiarism.app.utils.plagiarism.report;

import java.io.IOException;

public class ReportPlagiarismCheckUtilException extends RuntimeException {

    public ReportPlagiarismCheckUtilException() {
        super();
    }

    public ReportPlagiarismCheckUtilException(String message) {
        super(message);
    }

    public ReportPlagiarismCheckUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportPlagiarismCheckUtilException(Throwable cause) {
        super(cause);
    }

    protected ReportPlagiarismCheckUtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
