package ru.rzncenter.webcore.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.rzncenter.webcore.service.RemoveFilesQueueHolder;
import ru.rzncenter.webcore.utils.FilesRemover;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Удаляет файлы поставленные в очередь на удаление
 */
@Service
public class RemoveQueueJob {

    private final FilesRemover filesRemover;

    @Autowired
    public RemoveQueueJob(FilesRemover filesRemover) {
        this.filesRemover = filesRemover;
    }

    @Scheduled(fixedDelay = 1000)
    public void process() {
        Queue<String> queue = RemoveFilesQueueHolder.getInstance();
        List<String> files = new ArrayList<>();
        String name;
        while ((name = queue.poll()) != null) {
            files.add(name);
        }
        filesRemover.removeFiles(files);
    }

}
