package com.spotifyinfo.controllers;

import com.spotifyinfo.domain.UserDTO;
import com.spotifyinfo.domain.model.User;
import com.spotifyinfo.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController()
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @GetMapping()
    public UserDTO getUserByEmail(@RequestParam String email) {
        return usersService.getUserByEmail(email);
    };

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        usersService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body("User created successfully!");
    }
}
