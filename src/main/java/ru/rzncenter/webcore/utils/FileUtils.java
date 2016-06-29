package ru.rzncenter.webcore.utils;

import java.io.File;
import java.util.*;


public interface FileUtils {

    String getWebPath();

    String getServerPath();

    String webToServerPath(String path);

    String serverToWebPath(String path);

    String getSaveServerPath();

    String getNameForeSave(String folder, String name);

    boolean deleteFileByWebPath(String path);

    String getExtension(String path);

    boolean isImage(File file);

    String copy(String fullPath);

    SortedMap<Integer, String> copyFiles(Map<Integer, String> files);

}
