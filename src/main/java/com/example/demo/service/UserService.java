package com.example.demo.service;

import com.example.demo.dto.SignupRequest;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User registerUser(SignupRequest signupRequest) {
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail().toLowerCase().trim());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setPhoneNumber(signupRequest.getPhoneNumber());
      // Assuming this is for email verification

        // Assign role
        Role requestedRole = signupRequest.getRole();
        if (requestedRole == Role.ADMIN) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }
        return userRepository.save(user);
    }
}
