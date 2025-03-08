package com.courierit.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateParcelRequest {

    @JsonProperty("current_location")
    private String currentLocation;

    @JsonProperty("pickup_location")
    private String pickupLocation;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("weight")
    private float weight;
}