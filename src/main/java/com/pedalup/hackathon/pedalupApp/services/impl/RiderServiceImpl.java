package com.pedalup.hackathon.pedalupApp.services.impl;

import com.pedalup.hackathon.pedalupApp.dto.CycleDto;
import com.pedalup.hackathon.pedalupApp.dto.RideDto;
import com.pedalup.hackathon.pedalupApp.dto.RideRequestDto;
import com.pedalup.hackathon.pedalupApp.dto.RiderDto;
import com.pedalup.hackathon.pedalupApp.entities.*;
import com.pedalup.hackathon.pedalupApp.entities.enums.RideRequestStatus;
import com.pedalup.hackathon.pedalupApp.entities.enums.RideStatus;
import com.pedalup.hackathon.pedalupApp.exceptions.ResourceNotFoundException;
import com.pedalup.hackathon.pedalupApp.exceptions.RuntimeConflictException;
import com.pedalup.hackathon.pedalupApp.repositories.CycleRepository;
import com.pedalup.hackathon.pedalupApp.repositories.RideRepository;
import com.pedalup.hackathon.pedalupApp.repositories.RideRequestRepository;
import com.pedalup.hackathon.pedalupApp.repositories.RiderRepository;
import com.pedalup.hackathon.pedalupApp.services.CycleService;
import com.pedalup.hackathon.pedalupApp.services.RideService;
import com.pedalup.hackathon.pedalupApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
//    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final CycleRepository cycleRepository;
    private final RiderRepository riderRepository;
    private final RideRepository rideRepository;
    private final RideService rideService;
    private final CycleService cycleService;

    @Override
    @Transactional
    public RideDto requestRide(RideRequestDto rideRequestDto, String cycleId) {
        Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

//        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
//        Double fare = rideRequestDto.getFare();
//        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        Cycle cycle = cycleRepository.findByCycleId(cycleId).orElse(null);

        System.out.print(cycle);

        if(cycle == null)
            throw new RuntimeConflictException("Cannot SetUp Ride, Cycle is null not available with cycle Id: "+cycleId);

        if(cycle.getAvailable() == Boolean.FALSE){
            throw new RuntimeConflictException("Cannot SetUp Ride, Cycle is not available with cycle Id: "+cycleId);
        }

        cycle.setAvailable(Boolean.FALSE);

        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);
        savedRideRequest = rideRequestRepository.save(rideRequest);

        Cycle savedCycle = cycleRepository.save(cycle);

        Ride savedRide = rideService.createNewRide(savedRideRequest,savedCycle);

//        List<Cycle> drivers = rideStrategyManager
//                .driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);

//        TODO : Send notification to all the drivers about this ride request

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public RideDto endRide(Long rideId) {
        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);

        if(!rider.equals(ride.getRider())) {
            throw new RuntimeException(("Rider does not own this ride with id: "+rideId));
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride cannot be cancelled, invalid status: "+ride.getRideStatus());
        }

        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ENDED);
        cycleService.updateCycleAvailability(ride.getCycle(), true);
        ride.setEndedAt(LocalDateTime.now());
        savedRide = rideRepository.save(ride);

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public CycleDto rateCycle(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        Rider currentRider = getCurrentRider();
        return modelMapper.map(currentRider, RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider currentRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider, pageRequest).map(
                ride -> modelMapper.map(ride, RideDto.class)
        );
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return riderRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(
                "Rider not associated with user with id: "+user.getId()
        ));
    }

}
