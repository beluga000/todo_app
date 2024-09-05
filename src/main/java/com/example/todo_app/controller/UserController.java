package com.example.todo_app.controller;

import com.example.todo_app.model.User;
import com.example.todo_app.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "API for managing user accounts")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "새로운 사용자 생성", description = "POST 요청을 통해 새로운 사용자를 생성합니다. 생성된 사용자는 응답으로 반환됩니다.")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("사용자 정보가 유효하지 않습니다. 사용자 이름이 필요합니다.");
        }
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("사용자 생성 중 오류 발생: " + e.getMessage());
        }
    }

}