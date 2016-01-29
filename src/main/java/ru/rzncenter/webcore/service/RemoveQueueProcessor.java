package ru.rzncenter.webcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.rzncenter.webcore.utils.FilesRemover;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Удаляет файлы поставленные в очередь на удаление
 */
@Service
public class RemoveQueueProcessor {

    @Autowired
    FilesRemover filesRemover;


    @Scheduled(fixedDelay = 1000)
    public void process() {

        Queue<String> queue = RemoveFilesQueueHolder.getInstance();

        List<String> files = new ArrayList<>();

        while (!queue.isEmpty()) {

            files.add(queue.poll());

        }

        filesRemover.removeFiles(files);

    }

}