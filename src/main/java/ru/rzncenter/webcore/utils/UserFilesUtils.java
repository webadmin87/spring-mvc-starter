package ru.rzncenter.webcore.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Содержит методы дл работы с пользовательскими файлами
 */
@Component
public class UserFilesUtils implements FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFilesUtils.class);

    @Autowired
    private EnvironmentInfo info;

    @Autowired
    private Translation translation;

    /**
     * Возвращает путь к паке относительно веб сервера хранящей пользовательские файла
     * @return
     */
    @Override
    public String getWebPath() {
        return  info.getUserfilesDirName();
    }

    /**
     * Возвращает абсолютный путь к папке на сервере хранящей пользовательские файлы
     * @return
     */
    @Override
    public String getServerPath() {
        Path path = Paths.get(System.getenv(info.getUserfilesEnvVariable()), info.getApplicationName(), info.getUserfilesDirName());
        createIfNotExist(path);
        return path.toString();
    }

    /**
     * Конвертирует путь относительно веб сервера в абсолютный путь
     * @param path
     * @return
     */
    @Override
    public String webToServerPath(String path) {
        return  path.replaceFirst(getWebPath(), getServerPath());
    }

    /**
     * Конвертирует абсолютный путь в путь относительно веб сервера
     * @param path
     * @return
     */
    @Override
    public String serverToWebPath(String path) {
        return  path.replaceFirst(getServerPath(), getWebPath());
    }

    /**
     * Возвращает абсолютный путь для сохранения загружаемого файла
     * @return
     */
    @Override
    public String getSaveServerPath() {
        String dateFolder = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Path path = Paths.get(getServerPath(), dateFolder);
        createIfNotExist(path);
        return path.toString();
    }

    /**
     * Вохвращает свободное имя файла в папке
     * @param folder путь к папке
     * @param inpName желаемое имя файла
     * @return
     */
    @Override
    public String getNameForeSave(String folder, String inpName) {
        String name = translation.execute(inpName);
        if(Files.notExists(Paths.get(folder, name))) {
            return name;
        }
        List<String> parts = new ArrayList<>(Arrays.asList(name.split("\\.")));
        int size = parts.size();
        boolean hasExt = size > 1;
        String ext = null;
        if(hasExt) {
            ext =parts.get(size - 1);
            parts.remove(size-1);
        }
        String nameWithoutExt = String.join(".", parts);
        int i = 1;
        while (Files.exists(Paths.get(folder, name))) {
            name = nameWithoutExt + "(" + i + ")";
            if(hasExt) {
                name += "." + ext;
            }
            i++;
        }
        return name;
    }

    /**
     * Удаляет файл по относительному пути
     * @param path
     * @return
     */
    @Override
    public boolean deleteFileByWebPath(String path) {
        try {
            Files.deleteIfExists(Paths.get(webToServerPath(path)));
            return true;
        } catch (Exception e) {
            LOGGER.error("Error delete file", e);
            return false;
        }
    }

    /**
     * Возвращает расширение файла
     * @param path путь к файлу
     * @return
     */
    @Override
    public String getExtension(String path) {
        String ext = StringUtils.EMPTY;
        int i = path.lastIndexOf(".");
        if(i > -1) {
            ext = path.substring(i+1);
        }
        return ext;
    }

    /**
     * Является ли файл изображением
     * @param file
     * @return
     */
    @Override
    public boolean isImage(File file) {
        try {
            return isImage(Files.probeContentType(file.toPath()));

        } catch (Exception e) {
            LOGGER.error("Error probe content type", e);
            return false;
        }
    }

    @Override
    public boolean isImage(String mimeType) {
        String type = mimeType.split("/")[0];
        return type.equalsIgnoreCase("image");
    }

    private void createIfNotExist(Path path) {
        if(Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

}
