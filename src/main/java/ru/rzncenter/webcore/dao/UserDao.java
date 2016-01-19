package ru.rzncenter.webcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rzncenter.webcore.domains.User;

/**
 * Created by anton on 19.01.16.
 */
public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
