package ru.rzncenter.webcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import ru.rzncenter.webcore.dao.UserDao;
import ru.rzncenter.webcore.domains.Domain;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Сервис для CRUD операций
 * @param <T>
 */
public abstract class CrudServiceImpl<T extends Domain> implements CrudService<T> {

    @Autowired
    UserDao userDao;

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
        applyAuthor(domain);
        return getRepository().save(domain);
    }

    @Override
    @Transactional
    public void delete(T domain) {
        getRepository().delete(domain);
    }

    @Override
    public abstract JpaRepository<T, Long> getRepository();

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    protected void applyAuthor(T domain) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(domain.getAuthor() == null && authentication.isAuthenticated()) {

            domain.setAuthor(userDao.findByUsername(authentication.getName()));

        }

    }

}
