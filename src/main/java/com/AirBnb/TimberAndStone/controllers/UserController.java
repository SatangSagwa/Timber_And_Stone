package com.AirBnb.TimberAndStone.controllers;

import com.AirBnb.TimberAndStone.dtos.responses.rental.ContactResponse;
import com.AirBnb.TimberAndStone.dtos.responses.user.GetSingleUserResponse;
import com.AirBnb.TimberAndStone.responses.user.UserResponse;
import com.AirBnb.TimberAndStone.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSingleUserResponse> getUserById(@Valid @PathVariable String id) {
        GetSingleUserResponse getSingleUserResponse = userService.getUserById(id);

        // Return the map in the response body
        return new ResponseEntity<>(getSingleUserResponse, HttpStatus.OK);
    }

    @GetMapping("email/{email}")
    public ResponseEntity<List<UserResponse>> getUserByEmail(@Valid @PathVariable String email) {
        List<UserResponse> users = userService.getUserByEmail(email);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("contact/{id}")
    public ResponseEntity<ContactResponse> getUserContactInfo(@Valid @PathVariable String id) {
        ContactResponse response = userService.getUserContacts(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*@PatchMapping("/{id}")
    public ResponseEntity<?> activateOrDeactivateUser(@PathVariable String id) {
        ActivateDeactivateResponse response = userService.activateDeactivateUser(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }*/
}
