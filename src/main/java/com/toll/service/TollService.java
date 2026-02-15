package com.toll.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.toll.dto.*;
import com.toll.entity.TollPlaza;
import com.toll.exception.SamePincodeException;
import com.toll.repository.TollPlazaRepository;
import com.toll.util.GeoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TollService {

    private final TollPlazaRepository repository;
    private final GoogleService googleService;

    @Cacheable(value = "routes",
            key = "#source + '-' + #destination")
    public TollResponseDTO getTolls(String source,
                                    String destination) {

        if (source.equals(destination)) {
            throw new SamePincodeException(
                    "Source and destination pincodes cannot be the same");
        }

        // 1️⃣ Get source & destination coordinates
        Coordinates sourceCoord =
                googleService.getCoordinatesFromPincode(source);

        Coordinates destCoord =
                googleService.getCoordinatesFromPincode(destination);

        // 2️⃣ Get route data (distance + polyline points)
        RouteData routeData =
                googleService.getRoute(sourceCoord, destCoord);

        // 3️⃣ Get all tolls from DB
        List<TollPlaza> allTolls = repository.findAll();

        // 4️⃣ Filter tolls near route
        double thresholdKm = 2.0;

        List<TollPlazaDTO> filteredTolls =
                allTolls.stream()
                        .filter(toll ->
                                routeData.getPolylinePoints()
                                        .stream()
                                        .anyMatch(point ->
                                                GeoUtils.calculateDistance(
                                                        point.getLat(),
                                                        point.getLng(),
                                                        toll.getLatitude(),
                                                        toll.getLongitude())
                                                        <= thresholdKm))
                        .map(toll -> {

                            double distFromSource =
                                    GeoUtils.calculateDistance(
                                            sourceCoord.getLat(),
                                            sourceCoord.getLng(),
                                            toll.getLatitude(),
                                            toll.getLongitude());

                            return TollPlazaDTO.builder()
                                    .name(toll.getName())
                                    .latitude(toll.getLatitude())
                                    .longitude(toll.getLongitude())
                                    .distanceFromSource(distFromSource)
                                    .build();
                        })
                        .sorted((a, b) ->
                                Double.compare(a.getDistanceFromSource(),
                                        b.getDistanceFromSource()))
                        .toList();

        return TollResponseDTO.builder()
                .route(RouteDTO.builder()
                        .sourcePincode(source)
                        .destinationPincode(destination)
                        .distanceInKm(routeData.getDistanceInKm())
                        .build())
                .tollPlazas(filteredTolls)
                .build();
    }
}