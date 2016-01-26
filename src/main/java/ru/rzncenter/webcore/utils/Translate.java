package ru.rzncenter.webcore.utils;

/**
 * Интерфейс компонента переводов
 */
public interface Translate {

    public String t(String word);

    public String t(String word, Object[] params);

}
