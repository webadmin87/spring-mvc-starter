package ru.rzncenter.webcore.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rzncenter.webcore.dao.UserDao;
import ru.rzncenter.webcore.dao.UserSpec;
import ru.rzncenter.webcore.domains.Previews;
import ru.rzncenter.webcore.domains.User;
import ru.rzncenter.webcore.domains.UserFilter;
import ru.rzncenter.webcore.web.Resizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Сервис слой пользователей
 */
@Service
public class UserServiceImpl extends CrudServiceImpl<User> implements UserService {

    @Override
    public UserDao getRepository() {
        return getUserDao();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return getRepository().findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByToken(String token) {
        return getRepository().findByToken(token);
    }

    @Override
    @Transactional
    public User save(User domain) {
        if(domain.getInputPassword() != null && domain.getInputPassword().length()>0) {
            domain.setPassword(DigestUtils.md5Hex(domain.getInputPassword()));
        }
        domain.setToken(DigestUtils.md5Hex(domain.getUsername() + domain.getPassword() + domain.getRole()));
        return super.save(domain);
    }

    @Override
    @Transactional
    public void delete(User domain) {
        super.delete(domain);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(UserFilter filter, Integer page, Integer pageSize, Sort sort) {
        return getRepository().findAll(Specifications.where(UserSpec.filter(filter)), new PageRequest(page-1, pageSize, sort));
    }
}
