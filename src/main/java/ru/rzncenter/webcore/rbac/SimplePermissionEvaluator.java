package ru.rzncenter.webcore.rbac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class SimplePermissionEvaluator implements PermissionEvaluator {

    private final PermissionManager permissionManager;

    @Autowired
    public SimplePermissionEvaluator(PermissionManager permissionManager) {
        this.permissionManager = permissionManager;
    }

    public boolean hasPermission(Authentication authentication, Object target, Object permission) {
        String name = permission.toString();
        return permissionManager.hasPermission(name, authentication, target);
    }

    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        throw new UnsupportedOperationException();
    }

}