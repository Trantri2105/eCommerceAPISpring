package com.trantring.ecommerce.Service;

import com.trantring.ecommerce.DTO.CartItemDTO;
import com.trantring.ecommerce.DTO.Response.CartResponseDTO;
import com.trantring.ecommerce.Entity.Cart;
import com.trantring.ecommerce.Entity.Users;

public interface CartService {
    Cart save(Cart cart);

    void addProductToCart(String email, int productId, int quantity);

    CartResponseDTO getCartInfo(String email);

    CartResponseDTO updateCart(String email, CartItemDTO cartItemDTO);

    void deleteProductFromCart(String email, int productId);

    Cart findCartByUser(Users user);
}
