package ru.rzncenter.webcore.web.response;

import java.io.Serializable;

/**
 * Базовый класс для ответов
 */
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String errorMessage;

    public BaseResponse() {
    }

    public BaseResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
