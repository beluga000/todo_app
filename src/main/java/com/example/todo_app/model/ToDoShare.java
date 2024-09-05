package com.example.todo_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ToDoShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long toDoId;  // 공유되는 To-Do의 ID
    private Long ownerId;  // 공유한 사용자의 ID
    private Long sharedWithUserId;  // 공유받은 사용자의 ID

    // 기본 생성자
    public ToDoShare() {}

    // 파라미터가 있는 생성자
    public ToDoShare(Long toDoId, Long ownerId, Long sharedWithUserId) {
        this.toDoId = toDoId;
        this.ownerId = ownerId;
        this.sharedWithUserId = sharedWithUserId;
    }

    // Getter 및 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getToDoId() {
        return toDoId;
    }

    public void setToDoId(Long toDoId) {
        this.toDoId = toDoId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getSharedWithUserId() {
        return sharedWithUserId;
    }

    public void setSharedWithUserId(Long sharedWithUserId) {
        this.sharedWithUserId = sharedWithUserId;
    }
}