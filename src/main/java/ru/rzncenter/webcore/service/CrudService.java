package ru.rzncenter.webcore.service;

import org.springframework.data.repository.CrudRepository;

/**
 * Интерфейс сервиса CRUD операций
 * @param <T>
 */
public interface CrudService<T> {

    public Iterable<T> findAll();

    public T findOne(Long id);

    public T save(T domain);

    public void delete(T domain);

    abstract CrudRepository<T, Long> getRepository();

}
