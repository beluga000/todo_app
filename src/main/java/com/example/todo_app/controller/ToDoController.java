package com.example.todo_app.controller;

import com.example.todo_app.model.ToDo;
import com.example.todo_app.model.User;
import com.example.todo_app.service.ToDoService;
import com.example.todo_app.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
@Tag(name = "To-Do Controller", description = "API for managing To-Do items")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "새로운 할일 생성", description = "POST 요청을 통해 새로운 할일을 생성합니다. 생성된 할일은 응답으로 반환됩니다.")
    public ResponseEntity<?> createToDo(@RequestBody ToDo toDo) {
        if (toDo.getOwner() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("소유자(owner)가 null입니다.");
        }
        try {
            ToDo createdToDo = toDoService.createToDo(toDo);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdToDo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("할일 생성 중 오류 발생: " + e.getMessage());
        }
    }


    @GetMapping
    @Operation(summary = "모든 할일 조회", description = "GET 요청을 통해 모든 할일을 조회합니다.")
    public ResponseEntity<?> getAllToDos() {
        try {
            List<ToDo> todos = toDoService.getAllToDos();
            return ResponseEntity.ok(todos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("모든 할일 조회 중 오류 발생: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 할일 조회", description = "GET 요청을 통해 특정 ID의 할일을 조회합니다.")
    public ResponseEntity<ToDo> getToDoById(@PathVariable Long id) {
        Optional<ToDo> toDo = toDoService.getToDoById(id);
        return toDo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "할일 수정", description = "PUT 요청을 통해 특정 ID의 할일을 수정합니다. 수정된 할일은 응답으로 반환됩니다.")
    public ResponseEntity<?> updateToDo(@PathVariable Long id, @RequestBody ToDo toDo) {
        try {
            ToDo updatedToDo = toDoService.updateToDo(id, toDo);
            return updatedToDo != null ? ResponseEntity.ok(updatedToDo) 
                                       : ResponseEntity.status(HttpStatus.NOT_FOUND)
                                       .body("ID가 " + id + "인 할일을 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("할일 수정 중 오류 발생: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "할일 삭제", description = "DELETE 요청을 통해 특정 ID의 할일을 삭제합니다.")
    public ResponseEntity<?> deleteToDoById(@PathVariable Long id) {
        try {
            toDoService.deleteToDoById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("ID가 " + id + "인 할일 삭제 중 오류 발생: " + e.getMessage());
        }
    }

    @GetMapping("/owner/{ownerId}")
    @Operation(summary = "사용자가 작성한 할일 및 공유받은 할일 조회", description = "GET 요청을 통해 특정 사용자가 작성한 할일과 공유받은 할일을 모두 조회합니다.")
    public ResponseEntity<?> getToDosByOwnerId(@PathVariable Long ownerId) {
        User owner = userService.findUserById(ownerId);
        if (owner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("ID가 " + ownerId + "인 사용자를 찾을 수 없습니다.");
        }

        try {
            // 작성한 할일 조회
            List<ToDo> ownedToDos = toDoService.getToDosByOwner(owner);
            
            // 공유받은 할일 조회
            List<ToDo> sharedToDos = toDoService.getToDosSharedWithUser(owner);

            // 작성한 할일과 공유받은 할일을 합친 결과 반환
            ownedToDos.addAll(sharedToDos);
            return ResponseEntity.ok(ownedToDos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("사용자 ID가 " + ownerId + "인 할일 조회 중 오류 발생: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "할일 상태 변경", description = "PATCH 요청을 통해 할일의 상태(완료/미완료)를 변경합니다.")
    public ResponseEntity<?> updateToDoStatus(@PathVariable Long id, @RequestParam boolean completed) {
        try {
            ToDo updatedToDo = toDoService.updateToDoStatus(id, completed);
            return updatedToDo != null ? ResponseEntity.ok(updatedToDo) 
                                       : ResponseEntity.status(HttpStatus.NOT_FOUND)
                                       .body("ID가 " + id + "인 할일을 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("ID가 " + id + "인 할일 상태 변경 중 오류 발생: " + e.getMessage());
        }
    }
}