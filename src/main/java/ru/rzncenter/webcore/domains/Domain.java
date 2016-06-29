package ru.rzncenter.webcore.domains;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Базовый класс для всех сущностей
 */
@MappedSuperclass
public class Domain {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * Автор
     */
    @ManyToOne
    @JoinColumn(name="author_id", referencedColumnName = "id")
    User author;

    /**
     * Активность
     */
    @NotNull
    Boolean active = true;

    /**
     * Дата создания
     */
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

    /**
     * Дата изменения
     */
    @Temporal(TemporalType.TIMESTAMP)
    Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Boolean getActive() {
        return active != null && active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @PrePersist
    void prePersist() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = new Date();
    }

}
