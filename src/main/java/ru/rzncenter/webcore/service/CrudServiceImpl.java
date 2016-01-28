package ru.rzncenter.webcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Сервис для CRUD операций
 * @param <T>
 */
public abstract class CrudServiceImpl<T> implements CrudService<T> {

    protected Class<T> getGenericSuperClass() {

        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();

        return  (Class<T>) genericSuperclass.getActualTypeArguments()[0];

    }

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
    @Transactional
    public T save(T domain) {
        return getRepository().save(domain);
    }

    @Override
    @Transactional
    public void delete(T domain) {
        getRepository().delete(domain);
    }

    @Override
    public abstract JpaRepository<T, Long> getRepository();
}
