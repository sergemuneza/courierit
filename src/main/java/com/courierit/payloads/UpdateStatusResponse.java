package com.courierit.payloads;

import lombok.Data;

@Data
public class UpdateStatusResponse {
    private int status;
    private StatusData data;

    @Data
    public static class StatusData {
        private Long id;
        private String status;
        private String message;

        public StatusData(Long id, String status, String message) {
            this.id = id;
            this.status = status;
            this.message = message;
        }
    }
}
