package com.trantring.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemDTO {
    private int productId;
    private int quantity;
    private int price;
}
