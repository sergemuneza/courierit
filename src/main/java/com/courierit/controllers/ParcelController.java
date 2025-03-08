package com.courierit.controllers;

import com.courierit.payloads.*;
import com.courierit.services.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parcels")
public class ParcelController {

    @Autowired
    private ParcelService parcelService;

    // Endpoint to create a parcel
    @PostMapping
    public CreateParcelResponse createParcel(@RequestBody CreateParcelRequest request, @RequestParam Long userId) {
        return parcelService.createParcel(request, userId);
    }

    // Endpoint to fetch all parcels
    @GetMapping
    public FetchAllParcelsResponse fetchAllParcels() {
        return parcelService.fetchAllParcels();
    }

    // Endpoint to fetch a specific parcel by ID
    @GetMapping("/{parcelId}")
    public FetchParcelResponse fetchParcelById(@PathVariable Long parcelId) {
        return parcelService.fetchParcelById(parcelId);
    }

    // Endpoint to cancel a parcel
    @PutMapping("/{parcelId}/cancel")
    public CancelParcelResponse cancelParcel(@PathVariable Long parcelId, @RequestParam Long userId) {
        return parcelService.cancelParcel(parcelId, userId);
    }

    // Endpoint to change the destination of a parcel
    @PatchMapping("/{parcelId}/destination")
    public ChangeDestinationResponse changeDestination(@PathVariable Long parcelId, @RequestBody ChangeDestinationRequest request, @RequestParam Long userId) {
        return parcelService.changeDestination(parcelId, request, userId);
    }

    // Endpoint to update the status of a parcel (Admin Only)
    @PatchMapping("/{parcelId}/status")
    public UpdateStatusResponse updateStatus(@PathVariable Long parcelId, @RequestBody UpdateStatusRequest request, @RequestParam Long adminId) {
        return parcelService.updateStatus(parcelId, request, adminId);
    }

    // Endpoint to update the current location of a parcel (Admin Only)
    @PatchMapping("/{parcelId}/current_location")
    public UpdateCurrentLocationResponse updateCurrentLocation(@PathVariable Long parcelId, @RequestBody UpdateCurrentLocationRequest request, @RequestParam Long adminId) {
        return parcelService.updateCurrentLocation(parcelId, request, adminId);
    }
}