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
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }    

}