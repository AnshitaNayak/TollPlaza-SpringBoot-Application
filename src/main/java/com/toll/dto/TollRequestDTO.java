package com.toll.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TollRequestDTO {

    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{5}$",
            message = "Invalid source or destination pincode")
    private String sourcePincode;

    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{5}$",
            message = "Invalid source or destination pincode")
    private String destinationPincode;
}