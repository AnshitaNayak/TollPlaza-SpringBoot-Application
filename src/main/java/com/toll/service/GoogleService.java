package com.toll.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toll.dto.Coordinates;
import com.toll.dto.RouteData;
import com.toll.util.PolylineDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${google.api.key}")
    private String apiKey;

    // 1️⃣ Get coordinates from pincode
    public Coordinates getCoordinatesFromPincode(String pincode) {

        try {

            String url = "https://maps.googleapis.com/maps/api/geocode/json"
                    + "?address=" + pincode
                    + "&key=" + apiKey;

            String response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode root = objectMapper.readTree(response);

            if (!root.path("status").asText().equals("OK")) {
                throw new RuntimeException("Invalid pincode");
            }

            JsonNode location = root
                    .path("results")
                    .get(0)
                    .path("geometry")
                    .path("location");

            return new Coordinates(
                    location.path("lat").asDouble(),
                    location.path("lng").asDouble()
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch coordinates");
        }
    }

    // 2️⃣ Get route data (distance + polyline)
    public RouteData getRoute(Coordinates source, Coordinates dest) {

        try {

            String url = "https://maps.googleapis.com/maps/api/directions/json"
                    + "?origin=" + source.getLat() + "," + source.getLng()
                    + "&destination=" + dest.getLat() + "," + dest.getLng()
                    + "&key=" + apiKey;

            String response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode root = objectMapper.readTree(response);

            if (!root.path("status").asText().equals("OK")) {
                throw new RuntimeException("Google API error");
            }

            JsonNode route = root.path("routes").get(0);

            double distanceInMeters = route
                    .path("legs")
                    .get(0)
                    .path("distance")
                    .path("value")
                    .asDouble();

            String encodedPolyline = route
                    .path("overview_polyline")
                    .path("points")
                    .asText();

            List<Coordinates> polylinePoints =
                    PolylineDecoder.decode(encodedPolyline);

            return new RouteData(
                    distanceInMeters / 1000.0,
                    polylinePoints
            );

        }
        catch (Exception e) {
           throw new RuntimeException("Failed to fetch route");
        }
    }
}