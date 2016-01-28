package ru.rzncenter.webcore.web;

import ru.rzncenter.webcore.domains.Previews;

import java.io.File;
import java.util.List;

/**
 * Интерфейс компонента ресайза
 */
public interface Resizer {

    public String resize(File file, int width, int height);

    public String resize(File file, int width);

    public void resize(Previews model);

    public void resize(List models);

}
