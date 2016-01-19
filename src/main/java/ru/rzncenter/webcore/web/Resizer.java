package ru.rzncenter.webcore.web;

import java.io.File;

/**
 * Интерфейс компонента ресайза
 */
public interface Resizer {

    public String resize(File file, int width, int height);

    public String resize(File file, int width);

}
