package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository;


@Service
public class CustomerUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    // Spring Security calls this when someone tries to log in
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.example.demo.model.User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Return a UserDetails object (from Spring Security)
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword()) // must already be BCrypt-hashed
                .roles(user.getRole().name()) // convert Role enum to String
                .build();
    }
    
}
