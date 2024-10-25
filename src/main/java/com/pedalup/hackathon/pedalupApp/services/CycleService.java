package com.pedalup.hackathon.pedalupApp.services;

import com.pedalup.hackathon.pedalupApp.dto.CycleDto;
import com.pedalup.hackathon.pedalupApp.dto.PointDto;
import com.pedalup.hackathon.pedalupApp.entities.Cycle;

public interface CycleService {

    Cycle getCurrentCycle();

    Cycle updateCycleAvailability(Cycle cycle, boolean available);

    CycleDto updateLocation(CycleDto cycleDto, String cycleId);
}
