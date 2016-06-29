package ru.rzncenter.webcore.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Интерфейс загрузчика файлов
 */
public interface FileUploader {

    String handleFileUpload(MultipartFile file);

    String handleFileUploadFromUrl(String url);
}
