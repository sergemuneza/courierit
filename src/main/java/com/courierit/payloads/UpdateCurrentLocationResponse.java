package com.courierit.payloads;

import lombok.Data;

@Data
public class UpdateCurrentLocationResponse {
    private int status;
    private LocationData data;

    @Data
    public static class LocationData {
        private Long id;
        private String currentLocation;

        public LocationData(Long id, String currentLocation) {
            this.id = id;
            this.currentLocation = currentLocation;
        }
    }
}
