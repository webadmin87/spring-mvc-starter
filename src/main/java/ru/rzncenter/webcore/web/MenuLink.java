package ru.rzncenter.webcore.web;

import java.util.ArrayList;
import java.util.List;

/**
 * Модель ссылки меню
 */
public class MenuLink {

    private String title;

    private String link;

    private List<MenuLink> children =  new ArrayList<>();

    public MenuLink(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<MenuLink> getChildren() {
        return children;
    }

    public void setChildren(List<MenuLink> children) {
        this.children = children;
    }

    public MenuLink addChild(MenuLink link) {
        children.add(link);
        return this;
    }
}
