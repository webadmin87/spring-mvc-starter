package ru.rzncenter.webcore.rbac;

import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс менеджера прав доступа
 */
public interface PermissionManager {
    
    void addPermission(Permission permission);
    
    Permission getPermissonByName(String name);
    
    void reset();
    
    void linkToRole(String role, Permission permission);
    
    void linkToRole(String roleTo, String roleFrom);
    
    boolean isLinkedToRole(String role, Permission permission);

    Set<String> getRoles();
    
    boolean hasPermission(String name, Authentication auth, Object domain);

    boolean hasPermission(String name, Authentication auth);

    UserDomain getUserDomain(Authentication auth);

}
