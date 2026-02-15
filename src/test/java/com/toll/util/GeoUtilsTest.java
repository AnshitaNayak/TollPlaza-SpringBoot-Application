package com.toll.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WebMvcTest(GeoUtils.class)
class GeoUtilsTest {

    @Test
    void shouldReturnZeroDistanceForSameCoordinates() {
        double distance =
                GeoUtils.calculateDistance(12, 77, 12, 77);

        assertEquals(0, distance);
    }

    @Test
    void shouldReturnSmallDistance() {
        double distance =
                GeoUtils.calculateDistance(12, 77, 12.01, 77.01);

        assertTrue(distance > 0);
    }

    @Test
    void shouldReturnLargeDistance() {
        double distance =
                GeoUtils.calculateDistance(12, 77, 20, 80);

        assertTrue(distance > 500);
    }
}
