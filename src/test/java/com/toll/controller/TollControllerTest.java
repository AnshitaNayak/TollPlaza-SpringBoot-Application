package com.toll.controller;

import com.toll.dto.TollResponseDTO;
import com.toll.service.TollService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TollController.class)
class TollControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TollService tollService;

    @Test
    void shouldReturn200WhenValidRequest() throws Exception {

        when(tollService.getTolls(any(), any()))
                .thenReturn(
                        TollResponseDTO.builder()
                                .route(null)
                                .tollPlazas(List.of())
                                .build()
                );

        mockMvc.perform(post("/api/v1/toll-plazas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "sourcePincode":"560001",
                                  "destinationPincode":"560002"
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn400WhenInvalidInput() throws Exception {

        mockMvc.perform(post("/api/v1/toll-plazas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}