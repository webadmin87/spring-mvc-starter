package ru.rzncenter.webcore.utils;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        String path = System.getenv(info.getUserfilesEnvVariable()) + "/" + info.getApplicationName() + "/" + info.getUserfilesDirName();
        File dir = new File(path);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        return path;
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
        String userfiles = getServerPath();
        String dateFolder = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String path = userfiles + "/" + dateFolder;
        File dir = new File(path);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        return path;
    }

    /**
     * Вохвращает свободное имя файла в папке
     * @param folder путь к папке
     * @param name желаемое имя файла
     * @return
     */
    @Override
    public String getNameForeSave(String folder, String name) {
        name = translation.execute(name);
        String[] arr = name.split("\\.");
        ArrayList<String> parts = new ArrayList<>(Arrays.asList(arr)) ;
        Integer size = parts.size();
        Boolean hasExt = size>1;
        String ext = null;
        if(hasExt) {
            ext =parts.get(size - 1);
            parts.remove(size-1);
        }
        String nameWithoutExt = String.join(".", parts);
        Integer i = 1;
        while(new File(folder + "/" + name).exists()) {
            name = nameWithoutExt + "(" + i.toString() + ")";
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
            File file = new File(webToServerPath(path));
            file.delete();
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
        String ext = "";
        int i = path.lastIndexOf(".");
        if(i>-1) {
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
            Path path = file.toPath();
            String mimetype = Files.probeContentType(path);
            String type = mimetype.split("/")[0];
            return type.equals("image");
        } catch (Exception e) {
            LOGGER.error("Error probe content type", e);
            return false;
        }
    }

    /**
     * Крпирует файл
     * @param fullPath
     * @return возвращает путь к новому файлу
     */
    @Override
    public String copy(String fullPath) {
        String path  = FilenameUtils.getFullPathNoEndSeparator(fullPath);
        String name = FilenameUtils.getName(fullPath);
        String nameForSave  = getNameForeSave(path, name);
        String pathForSave = path + "/" + nameForSave;
        try {
            org.apache.commons.io.FileUtils.copyFile(new File(fullPath), new File(pathForSave));
        } catch (IOException e) {
            LOGGER.error("Copy error", e);
            return null;
        }
        return pathForSave;
    }

    /**
     * Копирует файлы
     * @param files
     * @return
     */
    @Override
    public SortedMap<Integer, String> copyFiles(Map<Integer, String> files) {
        if(files == null) {
            return null;
        }
        TreeMap<Integer, String> res = new TreeMap<>();
        for(Map.Entry<Integer, String> entry : files.entrySet()) {
            String serverPath = webToServerPath(entry.getValue());
            if(new File(serverPath).exists()) {
                String newServerPath = copy(serverPath);
                res.put(entry.getKey(), serverToWebPath(newServerPath));
            }
        }
        return res;
    }

}
