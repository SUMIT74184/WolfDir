package com.example.demo.controller;

// import com.example.demo.*;
import com.example.demo.dto.SignupRequest;
import com.example.demo.model.User;
import com.example.demo.payload.LoginRequest;
import com.example.demo.payload.LoginResponse;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;

import jakarta.validation.Valid;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest,
            BindingResult bindingResult) {
        // validation errors from @Valid
        if (bindingResult.hasErrors()) {
            String firstError = bindingResult.getFieldErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(Map.of("error", firstError));
        }

        try {
            User user = userService.registerUser(signupRequest);
            // if you require email verification, return 201 with message
            return ResponseEntity.status(201).body(Map.of(
                    "message", "User registered successfully. Please verify your email.",
                    "userId", user.getId()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            // log ex
            return ResponseEntity.status(500).body(Map.of("error", "Internal server error"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail().toLowerCase().trim(),
                            loginRequest.getPassword()));

            @SuppressWarnings("unused")
            var principal = authentication.getPrincipal();
            String username = authentication.getName();
            var roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String accessToken = jwtUtil.generateAccessToken(username, roles);
            String refreshToken = jwtUtil.generateRefreshToken(username);

            return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Invalid email or password");
        } catch (DisabledException ex) {
            return ResponseEntity.status(403).body("Account is disabled");

        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

}
