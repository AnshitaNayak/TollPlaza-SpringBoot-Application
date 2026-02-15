package com.toll.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RouteDTO {

    private String sourcePincode;
    private String destinationPincode;
    private double distanceInKm;
}