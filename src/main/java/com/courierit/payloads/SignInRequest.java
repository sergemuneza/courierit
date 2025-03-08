package com.courierit.payloads;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}