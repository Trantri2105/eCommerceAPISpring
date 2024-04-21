package com.trantring.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO {

    @NotBlank(message = "Address is required!")
    private String address;

    @NotBlank(message = "Phone number is required!")
    private String phoneNumber;
}
