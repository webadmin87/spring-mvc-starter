package ru.rzncenter.webcore.rbac;

import java.util.Set;

/**
 * Интерфейс прав доступа
 */
public interface Permission {
    
    String getName();
    
    void setName(String name);

    Rule getRule();

    void setRule(Rule rule);
    
    void addChild(Permission permission);
    
    void setParent(Permission permission);
    
    Permission getParent();
    
    Set<Permission> getChildren();

    boolean hasRule();
    
}
