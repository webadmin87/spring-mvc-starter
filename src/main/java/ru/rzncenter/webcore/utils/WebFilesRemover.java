package ru.rzncenter.webcore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Created by anton on 01.02.15.
 */
@Component
public class WebFilesRemover implements FilesRemover {

    @Autowired
    private FileUtils fileUtils;

    public void removeFiles(List<String> files) {
        if(files != null) {
            for (String name : files) {
                fileUtils.deleteFileByWebPath(name);
            }
        }
    }

}
