package com.courierit.services;

import com.courierit.entities.Parcel;
import com.courierit.entities.User;
import com.courierit.payloads.*;
import com.courierit.repositories.ParcelRepository;
import com.courierit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParcelService {

    @Autowired
    private ParcelRepository parcelRepository;

    @Autowired
    private UserRepository userRepository;

    // Method to create a parcel
    public CreateParcelResponse createParcel(CreateParcelRequest request, Long userId) {
        Parcel parcel = new Parcel();
        parcel.setUserId(userId);
        parcel.setPickupLocation(request.getPickupLocation());
        parcel.setDestination(request.getDestination());
        parcel.setWeight(request.getWeight());
        parcel.setCurrentLocation(request.getCurrentLocation());

        // Calculate price based on weight (example: $5 per kg)
        parcel.setPrice(request.getWeight() * 5);

        // Save the parcel to the database
        parcel = parcelRepository.save(parcel);

        // Create the response object
        CreateParcelResponse response = new CreateParcelResponse();
        response.setStatus(200);
        response.setMessage("Parcel delivery order successfully created");

        // Populate the ParcelData object
        CreateParcelResponse.ParcelData parcelData = new CreateParcelResponse.ParcelData();
        parcelData.setId(parcel.getId());
        parcelData.setStatus(parcel.getStatus());
        parcelData.setPickupLocation(parcel.getPickupLocation());
        parcelData.setDestination(parcel.getDestination());
        parcelData.setWeight(parcel.getWeight());
        parcelData.setPrice(parcel.getPrice());
        parcelData.setCurrentLocation(parcel.getCurrentLocation());
        parcelData.setCreatedOn(parcel.getCreatedOn());

        // Set the ParcelData object in the response
        response.setData(parcelData);

        return response;
    }

    // Method to fetch all parcels
    public FetchAllParcelsResponse fetchAllParcels() {
        List<Parcel> parcels = parcelRepository.findAll();

        FetchAllParcelsResponse response = new FetchAllParcelsResponse();
        response.setStatus(200);

        // Map parcels to ParcelData objects
        List<FetchAllParcelsResponse.ParcelData> parcelDataList = parcels.stream()
                .map(parcel -> {
                    FetchAllParcelsResponse.ParcelData parcelData = new FetchAllParcelsResponse.ParcelData();
                    parcelData.setId(parcel.getId());
                    parcelData.setStatus(parcel.getStatus());
                    parcelData.setPickupLocation(parcel.getPickupLocation());
                    parcelData.setDestination(parcel.getDestination());
                    parcelData.setWeight(parcel.getWeight());
                    parcelData.setPrice(parcel.getPrice());
                    parcelData.setCurrentLocation(parcel.getCurrentLocation());
                    parcelData.setCreatedOn(parcel.getCreatedOn());
                    return parcelData;
                })
                .collect(Collectors.toList());

        response.setData(parcelDataList);
        return response;
    }

    // Method to fetch a specific parcel by ID
    public FetchParcelResponse fetchParcelById(Long parcelId) {
        Parcel parcel = parcelRepository.findById(parcelId)
                .orElseThrow(() -> new RuntimeException("Parcel not found"));

        FetchParcelResponse response = new FetchParcelResponse();
        response.setStatus(200);

        // Populate the ParcelData object
        FetchParcelResponse.ParcelData parcelData = new FetchParcelResponse.ParcelData();
        parcelData.setId(parcel.getId());
        parcelData.setStatus(parcel.getStatus());
        parcelData.setPickupLocation(parcel.getPickupLocation());
        parcelData.setDestination(parcel.getDestination());
        parcelData.setWeight(parcel.getWeight());
        parcelData.setPrice(parcel.getPrice());
        parcelData.setCurrentLocation(parcel.getCurrentLocation());
        parcelData.setCreatedOn(parcel.getCreatedOn());

        response.setData(parcelData);
        return response;
    }

    // Method to cancel a parcel
    public CancelParcelResponse cancelParcel(Long parcelId, Long userId) {
        Parcel parcel = parcelRepository.findById(parcelId)
                .orElseThrow(() -> new RuntimeException("Parcel not found"));

        // Check if the parcel is already delivered
        if ("delivered".equals(parcel.getStatus())) {
            throw new RuntimeException("Sorry, it can't be done because it is not yet delivered.");
        }

        // Check if the user is the one who created the parcel
        if (!parcel.getUserId().equals(userId)) {
            throw new RuntimeException("Sorry, you can't cancel this order because you are not the creator.");
        }

        // Update the status to "canceled"
        parcel.setStatus("canceled");
        parcelRepository.save(parcel);

        CancelParcelResponse response = new CancelParcelResponse();
        response.setStatus(200);
        response.setData(new CancelParcelResponse.MessageData("Parcel order canceled successfully."));

        return response;
    }

    // Method to change the destination of a parcel
    public ChangeDestinationResponse changeDestination(Long parcelId, ChangeDestinationRequest request, Long userId) {
        Parcel parcel = parcelRepository.findById(parcelId)
                .orElseThrow(() -> new RuntimeException("Parcel not found"));

        // Check if the parcel is already delivered
        if ("delivered".equals(parcel.getStatus())) {
            throw new RuntimeException("Sorry, it can't be done because it is not yet delivered.");
        }

        // Check if the user is the one who created the parcel
        if (!parcel.getUserId().equals(userId)) {
            throw new RuntimeException("Sorry, you can't change the destination of this order because you are not the creator.");
        }

        // Update the destination
        parcel.setDestination(request.getNewDestination());
        parcelRepository.save(parcel);

        ChangeDestinationResponse response = new ChangeDestinationResponse();
        response.setStatus(200);
        response.setData(new ChangeDestinationResponse.DestinationData(
                parcel.getId(),
                parcel.getDestination(),
                "Parcel destination changed successfully."
        ));

        return response;
    }

    // Method to update the status of a parcel (Admin Only)
    public UpdateStatusResponse updateStatus(Long parcelId, UpdateStatusRequest request, Long adminId) {
        // Check if the user is an admin
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!admin.isAdmin()) {
            throw new RuntimeException("Sorry, it can't be done because you are not an admin.");
        }

        Parcel parcel = parcelRepository.findById(parcelId)
                .orElseThrow(() -> new RuntimeException("Parcel not found"));

        // Update the status
        parcel.setStatus(request.getStatus());
        parcelRepository.save(parcel);

        UpdateStatusResponse response = new UpdateStatusResponse();
        response.setStatus(200);
        response.setData(new UpdateStatusResponse.StatusData(
                parcel.getId(),
                parcel.getStatus(),
                "Admin has updated the status successfully."
        ));

        return response;
    }

    // Method to update the current location of a parcel (Admin Only)
    public UpdateCurrentLocationResponse updateCurrentLocation(Long parcelId, UpdateCurrentLocationRequest request, Long adminId) {
        // Check if the user is an admin
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!admin.isAdmin()) {
            throw new RuntimeException("Sorry, it can't be done because you are not an admin.");
        }

        Parcel parcel = parcelRepository.findById(parcelId)
                .orElseThrow(() -> new RuntimeException("Parcel not found"));

        // Update the current location
        parcel.setCurrentLocation(request.getCurrentLocation());
        parcelRepository.save(parcel);

        UpdateCurrentLocationResponse response = new UpdateCurrentLocationResponse();
        response.setStatus(200);
        response.setData(new UpdateCurrentLocationResponse.LocationData(
                parcel.getId(),
                parcel.getCurrentLocation()
        ));

        return response;
    }
}