package com.AirBnb.TimberAndStone.services;


import com.AirBnb.TimberAndStone.dto.ActivateDeactivateResponse;
import com.AirBnb.TimberAndStone.models.Role;
import com.AirBnb.TimberAndStone.models.User;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void registerUser(User user) {
        // hash password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // ensure the user has at least default role USER
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.USER));
        }
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return user;
    }

    public User getUserByEmail(String email) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email " + email + " not found.");
    }

    //Activates deactivated users, deactivates activated users.
    public ActivateDeactivateResponse activateDeactivateUser(String id) {
        User user = getUserById(id);

        //If user is active, set to false and return deactivated
        if(user.getActive()) {
            user.setActive(false);
            userRepository.save(user);
            return new ActivateDeactivateResponse("User has been deactivated", user.getUsername(), user.getActive());
        }

        //Set active to true and return activated
        user.setActive(true);
        userRepository.save(user);
        return new ActivateDeactivateResponse("User has been activated", user.getUsername(), user.getActive());
    }
}