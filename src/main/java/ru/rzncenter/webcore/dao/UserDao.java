package ru.rzncenter.webcore.dao;

import org.springframework.data.repository.CrudRepository;
import ru.rzncenter.webcore.domains.User;

/**
 * Created by anton on 19.01.16.
 */
public interface UserDao extends CrudRepository<User, Long> {

    User findByUsername(String username);

}
