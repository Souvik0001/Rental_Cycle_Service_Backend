package com.pedalup.hackathon.pedalupApp.services;

import com.pedalup.hackathon.pedalupApp.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long rideRequestId);

    void update(RideRequest rideRequest);
}
