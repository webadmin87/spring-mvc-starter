package ru.rzncenter.webcore.domains;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Файлы пользователя
 */
@Entity
public class UserFile extends FileDomain {

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
