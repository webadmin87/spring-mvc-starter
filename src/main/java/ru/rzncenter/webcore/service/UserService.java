package ru.rzncenter.webcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.rzncenter.webcore.domains.User;
import ru.rzncenter.webcore.domains.UserFilter;

/**
 * Интерфейс сервис слоя пользователей
 */
public interface UserService extends CrudService<User> {

    User findByUsername(String username);

    User findByToken(String token);

    Page<User> findAll(UserFilter filter, Integer page, Integer pageSize, Sort sort);

}
