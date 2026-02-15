package com.toll.util;

import com.toll.dto.Coordinates;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PolylineDecoderTest {

    @Test
    void shouldDecodeValidPolyline() {

        // Valid Google encoded polyline
        String encoded = "_p~iF~ps|U_ulLnnqC_mqNvxq`@";

        List<Coordinates> result = PolylineDecoder.decode(encoded);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
    }

    @Test
    void shouldReturnEmptyListForEmptyPolyline() {
        List<Coordinates> result = PolylineDecoder.decode("");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}