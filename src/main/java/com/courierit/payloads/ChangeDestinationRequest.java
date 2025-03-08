package com.courierit.payloads;

import lombok.Data;

@Data
public class ChangeDestinationRequest {
    private String newDestination;
}