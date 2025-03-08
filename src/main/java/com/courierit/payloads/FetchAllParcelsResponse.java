package com.courierit.payloads;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class FetchAllParcelsResponse {
    private int status;
    private List<ParcelData> data;

    @Data
    public static class ParcelData {
        private Long id;
        private String status;
        private String pickupLocation;
        private String destination;
        private float weight;
        private float price;
        private String currentLocation;
        private LocalDateTime createdOn;
    }
}

