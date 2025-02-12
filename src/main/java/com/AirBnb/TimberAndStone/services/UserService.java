package com.AirBnb.TimberAndStone.services;


import com.AirBnb.TimberAndStone.dto.ActivateDeactivateResponse;
import com.AirBnb.TimberAndStone.dto.RegisterRequest;
import com.AirBnb.TimberAndStone.dto.RegisterResponse;
import com.AirBnb.TimberAndStone.models.Address;
import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.models.Role;
import com.AirBnb.TimberAndStone.models.User;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        // check if username already exists
        if (existsByUsername(registerRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        // map the authRequest to a User entity
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setPassword(registerRequest.getPassword());

        //Set created and updated at to now.
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());

        //Set active to true when account is created.
        user.setActive(true);

        //Sets favorites to new empty array
        user.setFavouriteRentals(new ArrayList<Rental>());

        if(registerRequest.getProfilePhoto() == null || registerRequest.getProfilePhoto().isEmpty()) {
            user.setProfilePhoto("defaultprofile.jpg");
        } else {
            user.setProfilePhoto(registerRequest.getProfilePhoto());
        }

        // create an address
        Address address = new Address();
        address.setCountry(registerRequest.getCountry());
        address.setCity(registerRequest.getCity());
        address.setPostalCode(registerRequest.getPostalCode());
        address.setStreetName(registerRequest.getStreetName());
        address.setStreetNumber(registerRequest.getStreetNumber());
        address.setLatitude(registerRequest.getLatitude());
        address.setLongitude(registerRequest.getLongitude());

        // set users address to created address
        user.setAddress(address);

        // assign roles
        if (registerRequest.getRoles() == null || registerRequest.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.USER));
        } else {
            user.setRoles(registerRequest.getRoles());
        }

        // hash password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // ensure the user has at least default role USER
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.USER));
        }
        userRepository.save(user);
        return new RegisterResponse("User registered successfully",
                user.getUsername(),
                user.getRoles());
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