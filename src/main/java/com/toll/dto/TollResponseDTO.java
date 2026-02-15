package com.toll.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class TollResponseDTO {

    private RouteDTO route;
    private List<TollPlazaDTO> tollPlazas;
}