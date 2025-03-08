package com.courierit.payloads;

import lombok.Data;
import lombok.Setter;

@Data
public class SignUpRequest {
    private Long id;
    private String email;
    private String firstName; // Ensure this matches the JSON payload
    private String lastName;  // Ensure this matches the JSON payload
    private String password;
    private String phoneNumber;
    private String address;
    private boolean isAdmin;  // Ensure this matches the JSON payload

    // Explicit getter and setter for isAdmin
    public boolean getIsAdmin() {
        return isAdmin;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }
}