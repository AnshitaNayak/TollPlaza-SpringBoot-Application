package com.toll.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TollPlazaDTO {

    private String name;
    private Double latitude;
    private Double longitude;
    private Double distanceFromSource;
}