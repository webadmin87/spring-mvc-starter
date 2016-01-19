package ru.rzncenter.webcore.web;

/**
 * Интерфейс компонента переводов
 */
public interface Translate {

    public String t(String word);

    public String t(String word, Object[] params);

}
