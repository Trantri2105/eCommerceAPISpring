package com.trantring.ecommerce.DTO.Request;

import jakarta.validation.constraints.NotBlank;

public class OrderUpdateRequestDTO {

    @NotBlank(message = "Status is reuired!")
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
