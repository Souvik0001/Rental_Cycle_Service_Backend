package com.pedalup.hackathon.pedalupApp.services.impl;

import com.pedalup.hackathon.pedalupApp.entities.Cycle;
import com.pedalup.hackathon.pedalupApp.entities.Ride;
import com.pedalup.hackathon.pedalupApp.entities.RideRequest;
import com.pedalup.hackathon.pedalupApp.entities.Rider;
import com.pedalup.hackathon.pedalupApp.entities.enums.RideRequestStatus;
import com.pedalup.hackathon.pedalupApp.entities.enums.RideStatus;
import com.pedalup.hackathon.pedalupApp.exceptions.ResourceNotFoundException;
import com.pedalup.hackathon.pedalupApp.repositories.RideRepository;
import com.pedalup.hackathon.pedalupApp.services.RideRequestService;
import com.pedalup.hackathon.pedalupApp.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;

    @Override
    public Ride getRideById(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id: "+rideId));
    }


    @Override
    public Ride createNewRide(RideRequest rideRequest, Cycle cycle) {
        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);

        Ride ride = modelMapper.map(rideRequest, Ride.class);
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setCycle(cycle);
        ride.setStartedAt(LocalDateTime.now());
//        ride.setOtp(generateRandomOTP());
//        ride.setId(generateRandomId());

        rideRequestService.update(rideRequest);
        return rideRepository.save(ride);
    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest) {
        return rideRepository.findByRider(rider, pageRequest);
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Cycle cycle, PageRequest pageRequest) {
        return rideRepository.findByCycle(cycle, pageRequest);
    }

    private String generateRandomOTP() {
        Random random = new Random();
        int otpInt = random.nextInt(10000);  //0 to 9999
        return String.format("%04d", otpInt);
    }
}
