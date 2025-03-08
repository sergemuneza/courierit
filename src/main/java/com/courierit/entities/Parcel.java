package com.courierit.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Entity
@Table(name = "parcels")
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String status; // pending, in-transit, delivered, canceled

    @Column(name = "pickup_location")
    private String pickupLocation;

    private String destination;
    private float weight;
    private float price;

    @Column(name = "current_location")
    private String currentLocation;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    // Default status when a parcel is created
    @PrePersist
    public void setDefaultStatus() {
        this.status = "pending";
        this.createdOn = LocalDateTime.now();
    }
}