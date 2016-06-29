package ru.rzncenter.webcore.utils;

/**
 * Конфигуратор объектов
 * @param <T>
 */
public interface Configurator<T> {

    void configure(T object);

}
