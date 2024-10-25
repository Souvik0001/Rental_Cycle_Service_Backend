package com.pedalup.hackathon.pedalupApp.services;

import com.pedalup.hackathon.pedalupApp.entities.Cycle;
import com.pedalup.hackathon.pedalupApp.entities.Ride;
import com.pedalup.hackathon.pedalupApp.entities.RideRequest;
import com.pedalup.hackathon.pedalupApp.entities.Rider;
import com.pedalup.hackathon.pedalupApp.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {

    Ride getRideById(Long rideId);

    Ride createNewRide(RideRequest rideRequest, Cycle cycle);

    Ride updateRideStatus(Ride ride, RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest);

    Page<Ride> getAllRidesOfDriver(Cycle cycle, PageRequest pageRequest);
}
