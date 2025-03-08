package com.courierit.payloads;

import lombok.Data;

@Data
public class SignUpResponse {
    private int status;
    private String message;
    private UserData data;

    @Data
    public static class UserData {
        private String token;
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
    }
}