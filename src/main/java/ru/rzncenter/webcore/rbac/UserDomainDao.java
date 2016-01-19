package ru.rzncenter.webcore.rbac;

/**
 * Created by anton on 27.03.15.
 */
public interface UserDomainDao {

    public UserDomain findByUsername(String name);

}
