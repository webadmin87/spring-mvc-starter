package ru.rzncenter.webcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rzncenter.webcore.domains.User;

/**
 * Репозиторий для пользователей
 */
public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByToken(String token);

}
