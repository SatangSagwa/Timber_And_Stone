package com.AirBnb.TimberAndStone.controllers;

import com.AirBnb.TimberAndStone.dto.AuthRequest;
import com.AirBnb.TimberAndStone.dto.AuthResponse;
import com.AirBnb.TimberAndStone.dto.RegisterRequest;
import com.AirBnb.TimberAndStone.dto.RegisterResponse;
import com.AirBnb.TimberAndStone.services.UserService;
import com.AirBnb.TimberAndStone.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        /*
        // check if username already exists
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Username already exists");
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

        // create response object
        RegisterResponse response1 = new RegisterResponse(
                "User registered successfully",
                user.getUsername(),
                user.getRoles()
        );
         */

        // register the user using UserService
        RegisterResponse response = userService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody AuthRequest authRequest, HttpServletResponse response) {
        try {
            // authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
            // set authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // get User details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // generate JWT token
            String jwt = jwtUtil.generateToken(userDetails);
            ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwt)
                    .httpOnly(true) // prevents javascript to get cookie
                    .secure(false) // IMPORTANT TO CHANGE IN PRODUCTION TO TRUE
                    .path("/") // cookies is available in all application
                    .maxAge(10 * 60 * 60) // valid for 10h
                    .sameSite("Strict") // Lax & None
                    .build();
            // create response object
            AuthResponse authResponse = new AuthResponse(
                    "Login successful",
                    userDetails.getUsername(),
                    userService.findByUsername(userDetails.getUsername()).getRoles()
            );
            // return response with cookie-header and body
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(authResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect username or password");
        }
    }







}
