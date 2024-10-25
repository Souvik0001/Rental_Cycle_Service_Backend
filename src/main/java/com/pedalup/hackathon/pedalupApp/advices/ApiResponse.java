package com.pedalup.hackathon.pedalupApp.advices;

import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;

@Data
//@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true") // Allow only from this origin
public class ApiResponse<T> {

    private LocalDateTime timeStamp;
    private T data;
    private ApiError error;

    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }

    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }
}
