package com.example.todo_app.controller;

import com.example.todo_app.model.ToDoShare;
import com.example.todo_app.service.ToDoShareService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> shareToDo(@RequestParam Long toDoId, @RequestParam Long ownerId, @RequestParam Long sharedWithUserId) {
        try {
            ToDoShare sharedToDo = toDoShareService.shareToDoWithUser(toDoId, ownerId, sharedWithUserId);
            return ResponseEntity.ok(sharedToDo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("잘못된 요청: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("To-Do 공유 중 오류 발생: " + e.getMessage());
        }
    }

    // 특정 사용자가 공유받은 모든 To-Do 조회
    @GetMapping("/{userId}")
    @Operation(summary = "공유받은 To-Do 조회", description = "GET 요청을 통해 특정 사용자가 공유받은 모든 To-Do를 조회합니다.")
    public ResponseEntity<?> getSharedToDos(@PathVariable Long userId) {
        try {
            List<ToDoShare> sharedToDos = toDoShareService.getSharedToDosForUser(userId);
            if (sharedToDos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("ID가 " + userId + "인 사용자가 공유받은 To-Do가 없습니다.");
            }
            return ResponseEntity.ok(sharedToDos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("공유받은 To-Do 조회 중 오류 발생: " + e.getMessage());
        }
    }
}