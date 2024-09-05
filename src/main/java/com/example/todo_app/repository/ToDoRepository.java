package com.example.todo_app.repository;

import com.example.todo_app.model.ToDo;
import com.example.todo_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    List<ToDo> findByOwner(User owner);
}