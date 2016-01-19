package ru.rzncenter.webcore.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rzncenter.webcore.db.TreeDomain;
import ru.rzncenter.webcore.domains.KeyValue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Провайдер данных для выпадающих списков древовидных сущностей
 */
@Component
public class TreeListProvider {

    @Autowired
    Translate translate;

    /**
     * Формирует карту для заполнения выпадающего списка
     * @param resultList формируемая список
     * @param list коллекция корненвых элементов дерева
     * @param startLevel число начиная с которого будут формироваться отступы в списке
     * @param exclude домен который необходимо исключить из списка
     * @return
     */
    public List<KeyValue> getList(List<KeyValue> resultList, List<? extends TreeDomain> list, int startLevel, TreeDomain exclude) {

        for(TreeDomain domain : list) {

            if(exclude != null && exclude.equals(domain)) {
                continue;
            }

            String preffix = new String(new char[startLevel]).replace("\0", "-");

            KeyValue item = new KeyValue();

            item.setKey(domain.getId().toString());

            item.setValue(preffix + domain.getTitle());

            resultList.add(item);

            List<? extends TreeDomain> children = domain.getChildren();

            if(children != null && children.size() > 0) {

                getList(resultList, children, startLevel+1, exclude);

            }

        }

        return resultList;

    }

    public List<KeyValue> getList(List<? extends TreeDomain> list, TreeDomain exclude) {

        List<KeyValue> resultList = new ArrayList<>();

        return getList(resultList, list, 0, exclude);

    }


    public Translate getTranslate() {
        return translate;
    }

    public void setTranslate(Translate translate) {
        this.translate = translate;
    }
}
