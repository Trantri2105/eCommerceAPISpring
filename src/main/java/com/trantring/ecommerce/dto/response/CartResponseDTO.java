package com.trantring.ecommerce.dto.response;

import com.trantring.ecommerce.dto.CartItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartResponseDTO {
    private int totalPrice;
    private List<CartItemDTO> cartItems;
}
