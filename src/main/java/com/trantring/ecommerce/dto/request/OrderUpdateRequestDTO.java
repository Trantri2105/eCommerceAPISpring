package com.trantring.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateRequestDTO {

    @NotBlank(message = "Status is required!")
    String status;
}
