package ru.rzncenter.webcore.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.rzncenter.webcore.utils.FileUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Загрузчик файлов
 */
@Service
public class FileUploaderImpl implements FileUploader {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploaderImpl.class);

    @Autowired
    private FileUtils fileUtils;

    /**
     * Загрузка файла из формы
     *
     * @param file
     * @return
     */
    @Override
    synchronized public String handleFileUpload(MultipartFile file) {
        if (!file.isEmpty() && isFileValid(file)) {
            String name = file.getOriginalFilename();
            String folder = fileUtils.getSaveServerPath();
            String nameForSave = fileUtils.getNameForeSave(folder, name);
            Path path = Paths.get(folder, nameForSave);
            try {
                Files.write(path, file.getBytes());
                return fileUtils.serverToWebPath(path.toString());
            } catch (Exception e) {
                LOGGER.error("File upload error", e);
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Загрузка файла по url адресу
     *
     * @param u
     * @return
     */
    @Override
    synchronized public String handleFileUploadFromUrl(String u) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(u);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int response = connection.getResponseCode();
            if (response == 200) {
                String contentType = connection.getHeaderField("Content-type");
                if(!isFileValid(contentType)) {
                    return null;
                }
                MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
                MimeType fileType = allTypes.forName(contentType);
                String ext = fileType.getExtension();
                String name = DigestUtils.md2Hex(u);
                String folder = fileUtils.getSaveServerPath();
                String nameForSave = fileUtils.getNameForeSave(folder, name + ext);
                Path path = Paths.get(folder, nameForSave);
                Files.copy(connection.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                return fileUtils.serverToWebPath(path.toString());
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("File upload by url error", e);
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private boolean isFileValid(MultipartFile file) {
        return fileUtils.isImage(file.getContentType());
    }

    private boolean isFileValid(String mimeType) {
        return fileUtils.isImage(mimeType);
    }

}
