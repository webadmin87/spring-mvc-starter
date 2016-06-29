package ru.rzncenter.webcore.db;

import java.util.List;

/**
 * Интерфейс древовидной сущности
 */
public interface TreeDomain {

    TreeDomain getParent();

    List<? extends TreeDomain> getChildren();

    String getTitle();

    Long getId();

}
