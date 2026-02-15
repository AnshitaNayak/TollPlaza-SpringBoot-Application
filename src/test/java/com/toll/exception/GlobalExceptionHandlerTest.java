package com.toll.exception;

import com.toll.controller.TollController;
import com.toll.service.TollService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TollController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TollService tollService;

    @Test
    void shouldReturn500WhenServiceThrowsRuntimeException() throws Exception {

        // Arrange
        when(tollService.getTolls(any(), any()))
                .thenThrow(new RuntimeException("Failure"));

        // Act & Assert
        mockMvc.perform(post("/api/v1/toll-plazas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "sourcePincode":"560001",
                                  "destinationPincode":"560002"
                                }
                                """))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error")
                        .value("Something went wrong"));
    }
}