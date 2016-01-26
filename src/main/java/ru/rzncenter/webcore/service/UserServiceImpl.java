package ru.rzncenter.webcore.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rzncenter.webcore.dao.UserDao;
import ru.rzncenter.webcore.domains.User;

import java.util.ArrayList;
import java.util.Queue;

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

    @Override
    @Transactional
    public User save(User domain) {

        if(domain.getFilesToRemove().size()>0) {

            Queue<String> queue = RemoveFilesQueueHolder.getInstance();

            queue.addAll(domain.getFilesToRemove());

            domain.setFilesToRemove(new ArrayList<String>());

        }


        if(domain.getInputPassword() != null && domain.getInputPassword().length()>0) {

            domain.setPassword(DigestUtils.md5Hex(domain.getInputPassword()));

        }

        domain.setToken(DigestUtils.md5Hex(domain.getUsername() + domain.getPassword()));
        return super.save(domain);
    }

    @Override
    @Transactional
    public void delete(User domain) {

        if(domain.getFilePaths() != null && domain.getFilePaths().size() > 0) {

            Queue<String> queue = RemoveFilesQueueHolder.getInstance();

            queue.addAll(domain.getFilePaths().values());

        }

        super.delete(domain);
    }
}
