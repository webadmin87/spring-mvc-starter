package ru.rzncenter.webcore.domains;

import org.hibernate.validator.constraints.NotBlank;
import ru.rzncenter.webcore.service.RemoveFilesQueueHolder;

import javax.annotation.PreDestroy;
import javax.persistence.*;

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
    @Column(name = "id")
    private Long id;

    /**
     * Название
     */
    @NotBlank
    @Column(name = "title")
    private String title;

    /**
     * Путь к файлу
     */
    @NotBlank
    @Column(name = "path", nullable = false)
    private String path;

    /**
     * Сортировка
     */
    @Column(name="sort", nullable = false)
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
