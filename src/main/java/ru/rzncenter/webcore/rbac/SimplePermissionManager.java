package ru.rzncenter.webcore.rbac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Менеджер прав доступа
 */
@Component
public class SimplePermissionManager implements PermissionManager {

    Set<Permission> permissions = new HashSet<>();

    Map<String, Set<Permission>> links = new HashMap<>();

    @Autowired
    PermissionInitializer initializer;

    @Autowired
    UserDomainDao userDomainDao;

    @PostConstruct
    void init() {
        initializer.initialize(this);
    }

    @Override
    public void addPermission(Permission permission) {

        permissions.add(permission);

    }

    @Override
    public Permission getPermissonByName(String name) {

        for(Permission perm : permissions) {

            if(perm.getName().equals(name)) {

                return perm;

            }

        }

        return null;
    }

    @Override
    public void reset() {

        permissions.clear();
        links.clear();

    }

    Set<Permission> getPermissionLinksSet(String role) {

        Set<Permission> perms = links.get(role);

        if(perms == null) {

            perms = new HashSet<>();

            links.put(role, perms);

        }

        return perms;

    }

    @Override
    public void linkToRole(String role, Permission permission) {

        getPermissionLinksSet(role).add(permission);

    }

    @Override
    public void linkToRole(String roleTo, String roleFrom) {

        Set<Permission> to = getPermissionLinksSet(roleTo);

        Set<Permission> from = getPermissionLinksSet(roleFrom);

        to.addAll(from);

    }

    @Override
    public boolean isLinkedToRole(String role, Permission permission) {
        return getPermissionLinksSet(role).contains(permission);
    }


    @Override
    public Set<String> getRoles() {
        return links.keySet();
    }

    @Override
    public boolean hasPermission(String name, Authentication auth, Object domain) {

        Permission perm = getPermissonByName(name);

        if(perm == null) {

            return false;

        }

        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();

        boolean res = false;

        for(SimpleGrantedAuthority authority : authorities) {

            String roleName = authority.toString();

            if(isLinkedToRole(roleName, perm) && testPermission(perm, auth, domain)) {

                res = true;
                break;

            } else {

                Permission parent = perm.getParent();

                while(parent != null) {

                    if(isLinkedToRole(roleName, parent) && testPermission(parent, auth, domain)) {

                        res = true;
                        break;

                    }

                    parent = parent.getParent();
                }

                if(res == true)
                    break;

            }

        }

        return res;

    }

    @Override
    public boolean hasPermission(String name, Authentication auth) {
        return hasPermission(name, auth, new Object());
    }

    public UserDomain getUserDomain(Authentication auth) {

        UserDomain user = userDomainDao.findByUsername(auth.getName());

        if(user == null)
            throw new NullPointerException("User with "+auth.getName()+" not found");

        return user;

    }

    boolean testPermission(Permission perm, Authentication auth, Object domain) {

        return !perm.hasRule() || perm.getRule().execute(auth, getUserDomain(auth), domain);

    }
}
