package com.toll.service;

import com.toll.dto.Coordinates;
import com.toll.dto.RouteData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GoogleServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec uriSpec;

    @Mock
    private WebClient.RequestHeadersSpec headersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private GoogleService googleService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(any(String.class))).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    void shouldThrowWhenApiFails() {

        when(responseSpec.bodyToMono(any(Class.class)))
                .thenReturn(Mono.error(new RuntimeException("API error")));

        assertThrows(RuntimeException.class,
                () -> googleService.getCoordinatesFromPincode("472001"));
    }
}