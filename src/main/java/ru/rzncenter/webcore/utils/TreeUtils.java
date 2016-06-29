package ru.rzncenter.webcore.utils;

import ru.rzncenter.webcore.db.TreeDomain;
import java.util.List;

public interface TreeUtils {

    List<? extends TreeDomain> getParents(TreeDomain domain);

}
