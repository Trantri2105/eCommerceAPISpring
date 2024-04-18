package com.trantring.ecommerce.DTO.Response;

import com.trantring.ecommerce.DTO.CartItemDTO;

import java.util.ArrayList;
import java.util.List;

public class CartResponseDTO {
    private int totalPrice;
    private List<CartItemDTO> cartItems = new ArrayList<>();

    public CartResponseDTO(int totalPrice, List<CartItemDTO> cartItems) {
        this.totalPrice = totalPrice;
        this.cartItems = cartItems;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }
}
