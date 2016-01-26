package ru.rzncenter.webcore.service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Содержит очередь из файлов на удаление
 */
public class RemoveFilesQueueHolder {

    private RemoveFilesQueueHolder() {}

    private static class Holder {
        private final static Queue<String> instance = new ConcurrentLinkedQueue<>();
    }

    public static Queue<String> getInstance() {
        return Holder.instance;
    }


}
