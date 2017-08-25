package ru.rzncenter.webcore.utils;

import org.apache.commons.collections.CollectionUtils;
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

    @Override
    public void removeFiles(List<String> files) {
        if(CollectionUtils.isNotEmpty(files)) {
            files.forEach(name->fileUtils.deleteFileByWebPath(name));
        }
    }

}
