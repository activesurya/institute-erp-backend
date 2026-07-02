package com.instituterp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication and Authorization endpoints")
public class AuthController {

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticate user and return JWT token")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // TODO: Implement login logic
        return ResponseEntity.ok("Login endpoint");
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Register a new user account")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        // TODO: Implement registration logic
        return ResponseEntity.ok("Registration endpoint");
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh token", description = "Refresh JWT token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String token) {
        // TODO: Implement token refresh logic
        return ResponseEntity.ok("Token refreshed");
    }

    // Inner classes for request bodies
    public static class LoginRequest {
        public String username;
        public String password;
    }

    public static class RegisterRequest {
        public String username;
        public String email;
        public String password;
        public String firstName;
        public String lastName;
    }
}
