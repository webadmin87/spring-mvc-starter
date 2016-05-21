package ru.rzncenter.webcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.rzncenter.webcore.domains.User;

/**
 * Репозиторий для пользователей
 */
@Transactional(propagation = Propagation.MANDATORY)
public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);

    User findByToken(String token);

}
