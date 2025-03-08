package com.courierit.services;

import com.courierit.entities.User;
import com.courierit.payloads.SignUpRequest;
import com.courierit.payloads.SignUpResponse;
import com.courierit.payloads.SignInRequest;
import com.courierit.payloads.SignInResponse;
import com.courierit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    // Method to register a new user
    public SignUpResponse signUp(SignUpRequest request) {
        // Check if the email is already used
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("The email is already used. Please use another email.");
        }

        // Create a new user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Encrypt password
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setAdmin(request.isAdmin());

        // Save the user to the database
        user = userRepository.save(user);

        // Generate JWT token
        String token = jwtService.generateToken(user.getEmail());

        // Create the response object
        SignUpResponse response = new SignUpResponse();
        response.setStatus(201);
        response.setMessage("User created successfully");

        // Populate the UserData object
        SignUpResponse.UserData userData = new SignUpResponse.UserData();
        userData.setToken(token);
        userData.setId(user.getId());
        userData.setFirstName(user.getFirstName());
        userData.setLastName(user.getLastName());
        userData.setEmail(user.getEmail());

        response.setData(userData);
        return response;
    }

    // Method to load user details for authentication
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Convert the User entity to Spring Security's UserDetails
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.isAdmin() ? "ADMIN" : "USER") // Assign roles based on isAdmin
                .build();
    }

    // Method to authenticate a user
    public SignInResponse signIn(SignInRequest request) {
        // Find the user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify the password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate JWT token
        String token = jwtService.generateToken(user.getEmail());

        // Create the response object
        SignInResponse response = new SignInResponse();
        response.setStatus(200);

        // Populate the UserData object
        SignInResponse.UserData userData = new SignInResponse.UserData();
        userData.setToken(token);
        userData.setId(user.getId());
        userData.setFirstName(user.getFirstName());
        userData.setLastName(user.getLastName());
        userData.setEmail(user.getEmail());

        response.setData(userData);
        return response;
    }
}