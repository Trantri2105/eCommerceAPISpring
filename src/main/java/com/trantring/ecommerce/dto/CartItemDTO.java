package com.trantring.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItemDTO {

    @NotNull(message = "Product id is required!")
    private Integer productId;

    @NotNull(message = "Quantity is required!")
    @Min(value = 1, message = "Quantity must greater than 0!")
    private Integer quantity;

    private int price;
}
