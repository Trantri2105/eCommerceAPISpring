package com.trantring.ecommerce.DTO.Request;

import jakarta.validation.constraints.NotBlank;

public class OrderRequestDTO {

    @NotBlank(message = "Address is required!")
    private String address;

    @NotBlank(message = "Phone number is required!")
    private String phoneNumber;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
