package ru.rzncenter.webcore.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Интерфейс загрузчика файлов
 */
public interface FileUploader {

    public String handleFileUpload(MultipartFile file);

    public String handleFileUploadFromUrl(String url);
}
