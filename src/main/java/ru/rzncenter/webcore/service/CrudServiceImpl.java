package ru.rzncenter.webcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public Page<T> findAll(Integer page, Integer pageSize) {
        return getRepository().findAll(new PageRequest(page-1, pageSize));
    }

    @Override
    public Page<T> findAll(Integer page, Integer pageSize, Sort sort) {
        return getRepository().findAll(new PageRequest(page-1, pageSize, sort));
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
