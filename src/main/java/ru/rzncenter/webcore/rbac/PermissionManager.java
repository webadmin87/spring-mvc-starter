package ru.rzncenter.webcore.rbac;

import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс менеджера прав доступа
 */
public interface PermissionManager {
    
    public void addPermission(Permission permission);
    
    public Permission getPermissonByName(String name);
    
    public void reset();
    
    public void linkToRole(String role, Permission permission);
    
    public void linkToRole(String roleTo, String roleFrom);
    
    public boolean isLinkedToRole(String role, Permission permission);

    public Set<String> getRoles();
    
    public boolean hasPermission(String name, Authentication auth, Object domain);

    public boolean hasPermission(String name, Authentication auth);

    public UserDomain getUserDomain(Authentication auth);

}
