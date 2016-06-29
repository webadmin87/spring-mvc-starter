package ru.rzncenter.webcore.web;

import ru.rzncenter.webcore.db.TreeDomain;
import ru.rzncenter.webcore.domains.KeyValue;
import java.util.List;


public interface TreeListProvider {

    List<KeyValue> getList(List<KeyValue> resultList, List<? extends TreeDomain> list, int startLevel, TreeDomain exclude);

    List<KeyValue> getList(List<? extends TreeDomain> list, TreeDomain exclude);

}
