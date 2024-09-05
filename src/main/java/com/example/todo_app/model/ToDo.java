package com.example.todo_app.model;

import java.util.List;
import java.util.ArrayList;  

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;  // 일정 소유자

    @ManyToMany
    @JoinTable(
        name = "todo_shared_users",
        joinColumns = @JoinColumn(name = "todo_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> sharedUsers = new ArrayList<>();  // 일정을 공유받는 사용자들

    // 기본 생성자
    public ToDo() {}

    // 파라미터가 있는 생성자
    public ToDo(String title, String description, boolean completed, User owner) {
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.owner = owner;
    }

    // Getter 및 Setter
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getSharedUsers() {
        return sharedUsers;
    }

    public void setSharedUsers(List<User> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }
}