package ru.rzncenter.webcore.domains;

import ru.rzncenter.webcore.service.RemoveFilesQueueHolder;

import javax.annotation.PreDestroy;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * Базовый класс для сущностей файлов
 */
@MappedSuperclass
public class FileDomain {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название
     */
    @NotNull
    private String title;

    /**
     * Путь к файлу
     */
    @NotNull
    private String path;

    /**
     * Сортировка
     */
    private int sort = 500;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @PreDestroy
    public void preDestroy() {
        RemoveFilesQueueHolder.getInstance().add(getPath());
    }

}
