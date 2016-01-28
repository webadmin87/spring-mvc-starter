package ru.rzncenter.webcore.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rzncenter.webcore.dao.UserDao;
import ru.rzncenter.webcore.domains.Previews;
import ru.rzncenter.webcore.domains.User;
import ru.rzncenter.webcore.web.Resizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Сервис слой пользователей
 */
@Service
public class UserServiceImpl extends CrudServiceImpl<User> implements UserService {

    @Autowired
    UserDao repository;

    @Autowired
    Resizer resizer;

    @Override
    public UserDao getRepository() {
        return repository;
    }

    public void setRepository(UserDao repository) {
        this.repository = repository;
    }


    @Override
    public User findByUsername(String username) {
        return getRepository().findByUsername(username);
    }

    @Override
    public Page<User> findAll(Integer page, Integer pageSize, Sort sort) {

        Page<User> pageObj = super.findAll(page, pageSize, sort);

        resizer.resize(pageObj.getContent());

        return pageObj;

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
