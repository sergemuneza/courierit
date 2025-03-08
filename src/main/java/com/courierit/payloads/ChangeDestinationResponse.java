package com.courierit.payloads;

import lombok.Data;

@Data
public class ChangeDestinationResponse {
    private int status;
    private DestinationData data;

    @Data
    public static class DestinationData {
        private Long id;
        private String destination;
        private String message;

        public DestinationData(Long id, String destination, String message) {
            this.id = id;
            this.destination = destination;
            this.message = message;
        }
    }
}
