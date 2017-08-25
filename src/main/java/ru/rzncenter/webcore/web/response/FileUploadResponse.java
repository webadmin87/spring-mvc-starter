package ru.rzncenter.webcore.web.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by anton on 25.08.17.
 */
public class FileUploadResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> success;
    private List<String> error;

    public FileUploadResponse() {
    }

    public FileUploadResponse(List<String> success, List<String> error) {
        this.success = success;
        this.error = error;
    }

    public List<String> getSuccess() {
        return success;
    }

    public void setSuccess(List<String> success) {
        this.success = success;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "FileUploadResponse{" +
                "success=" + success +
                ", error=" + error +
                '}';
    }
}
