package ru.rzncenter.webcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Интерфейс сервиса CRUD операций
 * @param <T>
 */
public interface CrudService<T> {

    public List<T> findAll();

    public Page<T> findAll(Integer page, Integer pageSize);

    public T findOne(Long id);

    public T save(T domain);

    public void delete(T domain);

    abstract CrudRepository<T, Long> getRepository();

}
