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

    List<T> findAll();

    Page<T> findAll(Integer page, Integer pageSize);

    Page<T> findAll(Integer page, Integer pageSize, Sort sort);

    T findOne(Long id);

    T save(T domain);

    void delete(T domain);

    JpaRepository<T, Long> getRepository();

}
