package com.AirBnb.TimberAndStone.controllers;

import com.AirBnb.TimberAndStone.dtos.responses.user.ActivateDeactivateResponse;
import com.AirBnb.TimberAndStone.dtos.responses.rental.ContactResponse;
import com.AirBnb.TimberAndStone.models.User;
import com.AirBnb.TimberAndStone.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@Valid @PathVariable String id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("email/{email}")
    public ResponseEntity<User> getUserByEmail(@Valid @PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("contact/{id}")
    public ResponseEntity<ContactResponse> getUserContactInfo(@Valid @PathVariable String id) {
        ContactResponse response = userService.getUserContacts(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> activateOrDeactivateUser(@PathVariable String id) {
        ActivateDeactivateResponse response = userService.activateDeactivateUser(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
