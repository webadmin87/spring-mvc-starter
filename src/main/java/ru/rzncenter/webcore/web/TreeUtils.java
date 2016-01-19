package ru.rzncenter.webcore.web;

import org.springframework.stereotype.Component;
import ru.rzncenter.webcore.db.TreeDomain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Компонент для работы с древовидными сущностями
 */
@Component
public class TreeUtils {

    /**
     * Возвращает коллекцию родителей сущности
     * @param domain
     * @return
     */
    public List<? extends TreeDomain> getParents(TreeDomain domain) {

        List<TreeDomain> list = new ArrayList<>();

        TreeDomain parent;

        while( (parent=domain.getParent()) != null ) {

            list.add(parent);

            domain = parent;

        }

        Collections.reverse(list);

        return list;

    }

}
