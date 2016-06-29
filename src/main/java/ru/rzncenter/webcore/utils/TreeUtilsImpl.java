package ru.rzncenter.webcore.utils;

import org.springframework.stereotype.Component;
import ru.rzncenter.webcore.db.TreeDomain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Компонент для работы с древовидными сущностями
 */
@Component
public class TreeUtilsImpl implements TreeUtils {

    /**
     * Возвращает коллекцию родителей сущности
     * @param domain
     * @return
     */
    @Override
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
