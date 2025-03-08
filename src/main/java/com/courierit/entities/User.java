package com.courierit.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email") // Ensure email is unique
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Ensure email is unique and not null
    private String email;

    @Column(name = "first_name") // Ensure this matches the database column name
    private String firstName;

    @Column(name = "last_name") // Ensure this matches the database column name
    private String lastName;

    private String password;

    @Column(name = "phone_number") // Ensure this matches the database column name
    private String phoneNumber;

    private String address;
    @Column(name = "is_admin", columnDefinition = "TINYINT(1) DEFAULT 0") // Ensure isAdmin is saved as 0 or 1
    private boolean isAdmin;

    // Explicit getter and setter for isAdmin
    public boolean getIsAdmin() {
        return isAdmin;
    }

    public boolean isAdmin() {
        return true;
    }
}