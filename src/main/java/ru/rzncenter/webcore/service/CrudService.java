package ru.rzncenter.webcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Интерфейс сервиса CRUD операций
 * @param <T>
 */
public interface CrudService<T> {

    public List<T> findAll();

    public Page<T> findAll(Integer page, Integer pageSize);

    public Page<T> findAll(Integer page, Integer pageSize, Sort sort);

    public T findOne(Long id);

    public T save(T domain);

    public void delete(T domain);

    public JpaRepository<T, Long> getRepository();

}
