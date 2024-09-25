package org.assignment.dto;

import lombok.Data;

@Data
public class FailureResponse {
    private String errorMessage;

    public FailureResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
