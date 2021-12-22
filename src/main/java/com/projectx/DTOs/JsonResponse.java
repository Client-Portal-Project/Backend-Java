package com.projectx.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// For JSON response body
// These Lombok annotations reduce boiler plate code
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResponse {
    private Boolean success;
    private String message;
    private Object data;
}
