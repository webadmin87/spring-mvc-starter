package ru.rzncenter.webcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rzncenter.webcore.dao.UserDao;
import ru.rzncenter.webcore.domains.User;

/**
 * Сервис слой пользователей
 */
@Service
public class UserServiceImpl extends CrudServiceImpl<User> implements UserService {

    @Autowired
    UserDao repository;

    @Override
    public UserDao getRepository() {
        return repository;
    }

    public void setRepository(UserDao repository) {
        this.repository = repository;
    }
}
