package ru.rzncenter.webcore.utils;

/**
 * Конфигуратор объектов
 * @param <T>
 */
public interface Configurator<T> {

    public void configure(T object);

}
