package ru.rzncenter.webcore.rbac;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Простое правило доступа
 */
public class SimplePermission implements Permission {

    private String name;
    private Rule rule;
    private Set<Permission> children = new HashSet<>();
    private Permission parent;

    public SimplePermission(String name) {
        this.name = name;
    }

    public SimplePermission(String name, Rule rule) {
        this.name = name;
        this.rule = rule;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Rule getRule() {
        return rule;
    }

    @Override
    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public void addChild(Permission permission) {
        children.add(permission);
        permission.setParent(this);
    }

    @Override
    public void setParent(Permission permission) {
        parent = permission;
    }

    @Override
    public Permission getParent() {
        return parent;
    }

    @Override
    public Set<Permission> getChildren() {
        return children;
    }

    @Override
    public boolean hasRule() {
        return rule != null;
    }
}
