package ru.rzncenter.webcore.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import ru.rzncenter.webcore.constraints.FieldMatch;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import ru.rzncenter.webcore.constraints.Unique;
import ru.rzncenter.webcore.rbac.UserDomain;
import java.util.*;

/**
 * Пользователь
 */
@Entity
@Table(name="users")
@FieldMatch(first="inputPassword", second="confirmInputPassword")
@Unique("username")
@JsonIgnoreProperties(ignoreUnknown = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends Domain implements Previews, UserDomain {

    /**
     * Роли пользователя
     */
    public static enum Role {

        ROLE_ADMIN

    }

    /**
     * ФИО пользователя
     */
    @NotBlank
    String name;

    /**
     * Логин
     */
    @NotBlank
    @Size(min=3, max = 50)
    @Pattern(regexp = "^[A-z0-9_-]+$")
    @Column(unique = true)
    String username;

    /**
     * Пароль введенный пользователем
     */
    @Transient
    @Size(min=6)
    String inputPassword;

    /**
     * Подтверждение пароля введенного пользователем
     */
    @Transient
    String confirmInputPassword;

    /**
     * Пароль в зашифрованном виде
     */
    String password;

    /**
     * Email
     */
    @NotBlank
    @Email
    @Column(unique = true)
    String email;

    /**
     * Телефон
     */
    @NotBlank
    @Pattern(regexp = "^7[0-9]+$")
    @Size(min=11, max = 11)
    @Column(unique = true)
    String phone;

    /**
     * Изображения
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @SortNatural
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    SortedMap<Integer, String> filePaths;

    /**
     * Превью изображений
     */
    @Transient
    SortedMap<Integer, String> previews;

    /**
     * Изображения для удаления
     */
    @Transient
    List<String> filesToRemove = new ArrayList<String>();

    /**
     * Описание
     */
    @Column(columnDefinition = "text")
    String text;

    /**
     * Роль пользователя
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    Role role = Role.ROLE_ADMIN;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInputPassword() {
        return inputPassword;
    }

    public void setInputPassword(String inputPassword) {
        this.inputPassword = inputPassword;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getConfirmInputPassword() {
        return confirmInputPassword;
    }

    public void setConfirmInputPassword(String confirmInputPassword) {
        this.confirmInputPassword = confirmInputPassword;
    }

    public SortedMap<Integer, String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(SortedMap<Integer, String> filePaths) {
        this.previews = null;
        this.filePaths = filePaths;
    }

    public List<String> getFilesToRemove() {
        return filesToRemove;
    }

    public void setFilesToRemove(List<String> filesToRemove) {
        this.filesToRemove = filesToRemove;
    }

    @PrePersist
    @PreUpdate
    public void beforeSave() {

        setFilesToRemove(new ArrayList<String>());

    }

    @Override
    public SortedMap<Integer, String> getPreviews() {

        if(previews == null) {

            previews = new TreeMap<>(getFilePaths());

        }

        return previews;
    }

    @Override
    public void setPreviews(SortedMap<Integer, String> previews) {
        this.previews = previews;
    }

    @Override
    public int previewWidth() {
        return 150;
    }

    @Override
    public int previewHeight() {
        return 0;
    }

    @Override
    @JsonIgnore
    public User getAuthor() {
        return super.getAuthor();
    }

}
