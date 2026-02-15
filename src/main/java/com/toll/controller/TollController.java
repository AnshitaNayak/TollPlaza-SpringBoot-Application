package com.toll.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.toll.dto.TollRequestDTO;
import com.toll.service.TollService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TollController {

    private final TollService service;

    @PostMapping("/toll-plazas")
    public Object getTollPlazas(
            @Valid @RequestBody TollRequestDTO request){

        return service.getTolls(
                request.getSourcePincode(),
                request.getDestinationPincode());
    }
}