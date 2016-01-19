package ru.rzncenter.webcore.rbac;

import java.util.Set;

/**
 * Интерфейс прав доступа
 */
public interface Permission {
    
    public String getName();
    
    public void setName(String name);

    public Rule getRule();

    public void setRule(Rule rule);
    
    public void addChild(Permission permission);
    
    public void setParent(Permission permission);
    
    public Permission getParent();
    
    public Set<Permission> getChildren();

    public boolean hasRule();
    
}
