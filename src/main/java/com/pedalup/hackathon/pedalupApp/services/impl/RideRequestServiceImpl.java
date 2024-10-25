package com.pedalup.hackathon.pedalupApp.services.impl;

import com.pedalup.hackathon.pedalupApp.entities.RideRequest;
import com.pedalup.hackathon.pedalupApp.exceptions.ResourceNotFoundException;
import com.pedalup.hackathon.pedalupApp.repositories.RideRequestRepository;
import com.pedalup.hackathon.pedalupApp.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;

    @Override
    public RideRequest findRideRequestById(Long rideRequestId) {
        return rideRequestRepository.findById(rideRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with id: "+rideRequestId));
    }

    @Override
    public void update(RideRequest rideRequest) {
        rideRequestRepository.findById(rideRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with id: "+rideRequest.getId()));
        rideRequestRepository.save(rideRequest);
    }
}
