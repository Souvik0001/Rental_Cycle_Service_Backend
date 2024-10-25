package com.pedalup.hackathon.pedalupApp.services.impl;

import com.pedalup.hackathon.pedalupApp.dto.CycleDto;
import com.pedalup.hackathon.pedalupApp.dto.PointDto;
import com.pedalup.hackathon.pedalupApp.entities.Cycle;
import com.pedalup.hackathon.pedalupApp.exceptions.ResourceNotFoundException;
import com.pedalup.hackathon.pedalupApp.exceptions.RuntimeConflictException;
import com.pedalup.hackathon.pedalupApp.repositories.CycleRepository;
import com.pedalup.hackathon.pedalupApp.services.CycleService;
import com.pedalup.hackathon.pedalupApp.services.RideRequestService;
import com.pedalup.hackathon.pedalupApp.services.RideService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CycleServiceImpl implements CycleService {

    private final RideRequestService rideRequestService;
    private final CycleRepository cycleRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;


    @Override
    public Cycle getCurrentCycle() {
        return cycleRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("Cycle not found with " +
                "id "+2));
    }

    @Override
    public Cycle updateCycleAvailability(Cycle cycle, boolean available) {
        cycle.setAvailable(available);
        return cycleRepository.save(cycle);
    }

    @Override
    public CycleDto updateLocation(CycleDto cycleDto, String cycleId){
        Cycle cycle = cycleRepository.findByCycleId(cycleId).orElse(null);
        if(cycle == null)
            throw new RuntimeConflictException("Cannot SetUp Ride, Cycle is null not available with cycle Id: "+cycleId);
//        cycle.setCurrentLocation(modelMapper.map(pointDto, Cycle.class));
        Cycle updatedCycle = modelMapper.map(cycleDto, Cycle.class);

        cycle.setCurrentLocation(updatedCycle.getCurrentLocation());

//        return cycleRepository.save(updatedCycle);


        return modelMapper.map(cycleRepository.save(cycle), CycleDto.class);
    }
}
