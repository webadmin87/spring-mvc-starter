package ru.rzncenter.webcore.db;

import java.util.List;

/**
 * Интерфейс древовидной сущности
 */
public interface TreeDomain {

    public TreeDomain getParent();

    public List<? extends TreeDomain> getChildren();

    public String getTitle();

    public Long getId();

}
