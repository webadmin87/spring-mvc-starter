package ru.rzncenter.webcore.exceptions;

/**
 * Created by anton on 16.09.17.
 */
public class ApplicationException extends RuntimeException {

    private final String errorCode;

    public ApplicationException() {
        this(ErrorCodes.INTERNAL_ERROR);
    }

    public ApplicationException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public ApplicationException(String errorCode, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
