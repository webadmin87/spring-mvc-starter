package ru.rzncenter.webcore.web;

import ru.rzncenter.webcore.domains.Previews;

import java.io.File;
import java.util.List;

/**
 * Интерфейс компонента ресайза
 */
public interface Resizer {

    String resize(File file, int width, int height);

    String resize(File file, int width);

    void resize(Previews model);

    void resize(List models);

}
