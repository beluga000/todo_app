package com.example.todo_app.controller;

import com.example.todo_app.model.ToDoShare;
import com.example.todo_app.service.ToDoShareService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos/share")
public class ToDoShareController {

    @Autowired
    private ToDoShareService toDoShareService;

    // To-Do를 다른 사용자와 공유
    @PostMapping
    @Operation(summary = "To-Do 공유", description = "POST 요청을 통해 특정 To-Do를 다른 사용자와 공유합니다. 공유된 To-Do는 응답으로 반환됩니다.")
    public ResponseEntity<ToDoShare> shareToDo(@RequestParam Long toDoId, @RequestParam Long ownerId, @RequestParam Long sharedWithUserId) {
        ToDoShare sharedToDo = toDoShareService.shareToDoWithUser(toDoId, ownerId, sharedWithUserId);
        return ResponseEntity.ok(sharedToDo);
    }

    // 특정 사용자가 공유받은 모든 To-Do 조회
    @GetMapping("/{userId}")
    @Operation(summary = "공유받은 To-Do 조회", description = "GET 요청을 통해 특정 사용자가 공유받은 모든 To-Do를 조회합니다.")
    public ResponseEntity<List<ToDoShare>> getSharedToDos(@PathVariable Long userId) {
        List<ToDoShare> sharedToDos = toDoShareService.getSharedToDosForUser(userId);
        return ResponseEntity.ok(sharedToDos);
    }
}