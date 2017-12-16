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
    @Column(name = "id")
    private Long id;

    /**
     * Автор
     */
    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;

    /**
     * Активность
     */
    @NotNull
    @Column(name = "active", nullable = false)
    private boolean active = true;

    /**
     * Дата создания
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdat", nullable = false)
    private Date createdAt;

    /**
     * Дата изменения
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updatedat", nullable = false)
    private Date updatedAt;

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

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
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
