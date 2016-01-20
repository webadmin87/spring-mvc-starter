package ru.rzncenter.webcore.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Сервис для CRUD операций
 * @param <T>
 */
public abstract class CrudServiceImpl<T> implements CrudService<T> {

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public T findOne(Long id) {
        return getRepository().findOne(id);
    }

    @Override
    public T save(T domain) {
        return getRepository().save(domain);
    }

    @Override
    public void delete(T domain) {
        getRepository().delete(domain);
    }

    @Override
    public abstract JpaRepository<T, Long> getRepository();
}
