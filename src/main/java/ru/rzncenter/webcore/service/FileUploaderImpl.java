package ru.rzncenter.webcore.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.rzncenter.webcore.utils.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Загрузчик файлов
 */
@Service
public class FileUploaderImpl implements FileUploader {


    @Autowired
    FileUtils fileUtils;

    /**
     * Загрузка файла из формы
     * @param file
     * @return
     */
    @Override
    public String handleFileUpload(MultipartFile file) {

       BufferedOutputStream stream = null;

       try {

           if (!file.isEmpty()) {

               byte[] bytes = file.getBytes();

               String name = file.getOriginalFilename();

               String folder = fileUtils.getSaveServerPath();
               String nameForSave = fileUtils.getNameForeSave(folder, name);
               String path = folder + "/" + nameForSave;

               stream = new BufferedOutputStream(new FileOutputStream(new File(path)));

               stream.write(bytes);

               return fileUtils.serverToWebPath(path);

           } else {

               return null;

           }

       } catch (IOException e) {

           e.printStackTrace();
           return null;

       } finally {

           if(stream != null)
               try { stream.close(); } catch (IOException e) {}

       }

    }

    /**
     * Загрузка файла по url адресу
     * @param u
     * @return
     */
    @Override
    public String handleFileUploadFromUrl(String u) {

        HttpURLConnection connection = null;

        try {

            URL url = new URL(u);

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int response = connection.getResponseCode();

            if(response == 200) {

                String contentType = connection.getHeaderField("Content-type");

                MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();

                MimeType fileType = allTypes.forName(contentType);

                String ext = fileType.getExtension();

                String name = DigestUtils.md2Hex(u);

                String folder = fileUtils.getSaveServerPath();

                String nameForSave = fileUtils.getNameForeSave(folder, name + ext);

                String path = folder + "/" + nameForSave;

                org.apache.commons.io.FileUtils.copyInputStreamToFile(connection.getInputStream(), new File(path));

                return fileUtils.serverToWebPath(path);

            } else {

                return null;

            }

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {
            if(connection != null)
                connection.disconnect();
        }

    }
}
