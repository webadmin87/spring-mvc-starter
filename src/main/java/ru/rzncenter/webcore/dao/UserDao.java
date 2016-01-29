package ru.rzncenter.webcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.rzncenter.webcore.domains.User;

/**
 * Репозиторий для пользователей
 */
public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);

    User findByToken(String token);

}
