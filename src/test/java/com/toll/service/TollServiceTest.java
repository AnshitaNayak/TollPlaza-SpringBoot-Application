package com.toll.service;

import com.toll.dto.*;
import com.toll.entity.TollPlaza;
import com.toll.repository.TollPlazaRepository;
import com.toll.service.GoogleService;
import com.toll.service.TollService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TollServiceTest {

    @Mock
    private TollPlazaRepository repository;

    @Mock
    private GoogleService googleService;

    @InjectMocks
    private TollService tollService;

    private Coordinates source;
    private Coordinates destination;

    @BeforeEach
    void setup() {
        source = new Coordinates(12.0, 77.0);
        destination = new Coordinates(13.0, 78.0);
    }

    @Test
    void shouldThrowExceptionWhenPincodesAreSame() {
        assertThrows(RuntimeException.class,
                () -> tollService.getTolls("560001", "560001"));
    }

    @Test
    void shouldReturnSingleToll() {

        RouteData routeData =
                new RouteData(100,
                        List.of(new Coordinates(12.1, 77.1)));

        TollPlaza toll = TollPlaza.builder()
                .name("Test Toll")
                .latitude(12.1)
                .longitude(77.1)
                .build();

        when(googleService.getCoordinatesFromPincode("560001"))
                .thenReturn(source);

        when(googleService.getCoordinatesFromPincode("560002"))
                .thenReturn(destination);

        when(googleService.getRoute(source, destination))
                .thenReturn(routeData);

        when(repository.findAll())
                .thenReturn(List.of(toll));

        TollResponseDTO response =
                tollService.getTolls("560001", "560002");

        assertEquals(1, response.getTollPlazas().size());
    }

    @Test
    void shouldReturnEmptyWhenNoTollFound() {

        when(googleService.getCoordinatesFromPincode(any()))
                .thenReturn(source);

        when(googleService.getRoute(any(), any()))
                .thenReturn(new RouteData(100,
                        List.of(new Coordinates(50, 50))));

        when(repository.findAll())
                .thenReturn(List.of());

        TollResponseDTO response =
                tollService.getTolls("560001", "560002");

        assertTrue(response.getTollPlazas().isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenNoTollFound() {

        RouteData routeData =
                new RouteData(100,
                        List.of(new Coordinates(50.0, 50.0)));

        when(googleService.getCoordinatesFromPincode(any()))
                .thenReturn(source);

        when(googleService.getRoute(any(), any()))
                .thenReturn(new RouteData(100,
                        List.of(new Coordinates(50, 50))));

        when(repository.findAll())
                .thenReturn(List.of());

        TollResponseDTO response =
                tollService.getTolls("560001", "560002");

        assertTrue(response.getTollPlazas().isEmpty());
    }

    @Test
    void shouldReturnMultipleTolls() {

        // Mock coordinates
        Coordinates source = new Coordinates(12.0, 77.0);
        Coordinates destination = new Coordinates(13.0, 78.0);

        // Mock route polyline
        RouteData routeData = new RouteData(
                100,
                List.of(
                        new Coordinates(12.1, 77.1),
                        new Coordinates(12.2, 77.2)
                )
        );

        // Mock tolls
        TollPlaza t1 = TollPlaza.builder()
                .name("Toll1")
                .latitude(12.1)
                .longitude(77.1)
                .build();

        TollPlaza t2 = TollPlaza.builder()
                .name("Toll2")
                .latitude(12.2)
                .longitude(77.2)
                .build();

        // Stub GoogleService
        when(googleService.getCoordinatesFromPincode("560001"))
                .thenReturn(source);

        when(googleService.getCoordinatesFromPincode("560002"))
                .thenReturn(destination);

        when(googleService.getRoute(source, destination))
                .thenReturn(routeData);

        // Stub repository
        when(repository.findAll())
                .thenReturn(List.of(t1, t2));

        // Execute
        TollResponseDTO response =
                tollService.getTolls("560001", "560002");

        // Assert
        assertEquals(2, response.getTollPlazas().size());
    }

    @Test
    void shouldHandleGoogleServiceFailure() {

        when(googleService.getCoordinatesFromPincode(any()))
                .thenThrow(new RuntimeException("Google API error"));

        assertThrows(RuntimeException.class,
                () -> tollService.getTolls("560001", "560002"));
    }
}