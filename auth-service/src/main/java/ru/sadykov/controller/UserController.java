package ru.sadykov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sadykov.service.UserService;

@RestController
@RequestMapping("/api/v1/user")

@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> userExists(@PathVariable Long userId) {
        boolean userExists = userService.userExists(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userExists);
    }
}
