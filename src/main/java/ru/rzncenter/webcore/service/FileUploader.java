package ru.rzncenter.webcore.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by anton on 24.08.15.
 */
public interface FileUploader {

    public String handleFileUpload(MultipartFile file);

    public String handleFileUploadFromUrl(String url);
}
