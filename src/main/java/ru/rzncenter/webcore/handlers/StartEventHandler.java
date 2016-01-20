package ru.rzncenter.webcore.handlers;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import ru.rzncenter.webcore.dao.UserDao;
import ru.rzncenter.webcore.domains.User;

/**
 * Обработчик старта контекста приложения
 */
@Component
public class StartEventHandler implements ApplicationListener{

    @Autowired
    UserDao userDao;

    @Transactional
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof ContextRefreshedEvent) {
            initAdminUser();
        }

    }

    /**
     * Добавляем в БД администратора, если он еще не задан
     */
    private void initAdminUser() {
        
        User u = userDao.findByUsername("admin");
        
        if(u == null) {

            u = new User();

            u.setUsername("admin");

            u.setName("admin");

            u.setEmail("webadmin87@gmail.com");

            u.setPhone("79999999999");

            u.setInputPassword("password");
            u.setConfirmInputPassword("password");

            u.setRole(User.Role.ROLE_ADMIN);

            userDao.save(u);

        }
        
        
    }
    
}