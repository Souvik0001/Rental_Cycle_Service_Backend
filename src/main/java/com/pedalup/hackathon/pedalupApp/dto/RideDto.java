package com.pedalup.hackathon.pedalupApp.dto;

import com.pedalup.hackathon.pedalupApp.entities.enums.PaymentMethod;
import com.pedalup.hackathon.pedalupApp.entities.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideDto {

    private Long id;
//    private PointDto pickupLocation;
//    private PointDto dropOffLocation;

    private LocalDateTime createdTime;
    private RiderDto rider;
    private CycleDto cycle;
    private PaymentMethod paymentMethod;

    private RideStatus rideStatus;

//    private String otp;

    private Double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
