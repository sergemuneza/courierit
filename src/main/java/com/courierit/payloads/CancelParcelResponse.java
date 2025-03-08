package com.courierit.payloads;

import lombok.Data;

@Data
public class CancelParcelResponse {
    private int status;
    private MessageData data;

    @Data
    public static class MessageData {
        private String message;

        public MessageData(String message) {
            this.message = message;
        }
    }
}

