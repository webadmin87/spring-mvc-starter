package ru.rzncenter.webcore.utils;

/**
 * Интерфейс компонента переводов
 */
public interface Translate {

    String t(String word);

    String t(String word, Object[] params);

}
