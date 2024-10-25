package com.pedalup.hackathon.pedalupApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CycleDto {

    private Long id;
    private Double rating;
    private Boolean available;
    private String cycleId;
    private PointDto currentLocation;
}
