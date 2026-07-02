package com.instituterp.controller;

import com.instituterp.dto.AuthResponse;
import com.instituterp.dto.LoginRequest;
import com.instituterp.dto.RegisterRequest;
import com.instituterp.service.AuthenticationService;
import com.instituterp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;
    private AuthResponse authResponse;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");

        registerRequest = new RegisterRequest();
        registerRequest.setUsername("newuser");
        registerRequest.setEmail("new@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFirstName("New");
        registerRequest.setLastName("User");

        authResponse = new AuthResponse();
        authResponse.setToken("jwt-token");
        authResponse.setUserId(1L);
        authResponse.setUsername("testuser");
        authResponse.setEmail("test@example.com");
    }

    @Test
    void testLogin_Success() {
        when(authenticationService.login(any(LoginRequest.class))).thenReturn(authResponse);

        ResponseEntity<AuthResponse> response = authController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("jwt-token", response.getBody().getToken());
        verify(authenticationService, times(1)).login(any(LoginRequest.class));
    }

    @Test
    void testRegister_Success() {
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(authResponse);

        ResponseEntity<AuthResponse> response = authController.register(registerRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(authenticationService, times(1)).register(any(RegisterRequest.class));
    }

    @Test
    void testValidateToken_Valid() {
        when(authenticationService.validateToken("jwt-token")).thenReturn(true);

        ResponseEntity<Boolean> response = authController.validateToken("Bearer jwt-token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    void testValidateToken_Invalid() {
        when(authenticationService.validateToken("invalid-token")).thenReturn(false);

        ResponseEntity<Boolean> response = authController.validateToken("Bearer invalid-token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody());
    }
}
