package com.courier.courierapp.controller;

import com.courier.courierapp.dto.UserDTO;
import com.courier.courierapp.model.Users;
import com.courier.courierapp.security.JwtUtils;
import com.courier.courierapp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        // Hash the password
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);

        // Create a new user in the DB
        try {
            Users newUser = usersService.createUser(userDTO);
            // Generate a JWT token for the new user
            String token = jwtUtils.generateToken(newUser.getUsername());

            return ResponseEntity.ok("User registered successfully! Bearer " + token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating user: " + e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        // Проверяваме дали username/password са валидни
        UsernamePasswordAuthenticationToken authReq =
                new UsernamePasswordAuthenticationToken(
                        userDTO.getUsername(),
                        userDTO.getPassword()
                );
        authenticationManager.authenticate(authReq);

        // Генерираме JWT токен
        String token = jwtUtils.generateToken(userDTO.getUsername());

        return ResponseEntity.ok("Bearer " + token);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok("Logout successful (token invalidated client-side).");
    }
}
