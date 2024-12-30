package com.youcode.aptio.controller;

import com.youcode.aptio.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
//        return ResponseEntity.ok(userService.saveUser(userDto));
//    }

}
