package com.pedalup.hackathon.pedalupApp.services;

import com.pedalup.hackathon.pedalupApp.dto.CycleDto;
import com.pedalup.hackathon.pedalupApp.dto.RideDto;
import com.pedalup.hackathon.pedalupApp.dto.RideRequestDto;
import com.pedalup.hackathon.pedalupApp.dto.RiderDto;
import com.pedalup.hackathon.pedalupApp.entities.Rider;
import com.pedalup.hackathon.pedalupApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RiderService {

    RideDto requestRide(RideRequestDto rideRequestDto,String cycleId);

    RideDto endRide(Long rideId);

    CycleDto rateCycle(Long rideId, Integer rating);

    RiderDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Rider createNewRider(User user);

    Rider getCurrentRider();
}
