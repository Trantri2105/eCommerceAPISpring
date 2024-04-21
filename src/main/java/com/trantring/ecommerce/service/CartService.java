package com.trantring.ecommerce.service;

import com.trantring.ecommerce.dto.CartItemDTO;
import com.trantring.ecommerce.entity.Cart;
import com.trantring.ecommerce.entity.Users;

public interface CartService {
    Cart save(Cart cart);

    void addProductToCart(String email, int productId, int quantity);

    Cart getCartInfo(String email);

    Cart updateCart(String email, CartItemDTO cartItemDTO);

    void deleteProductFromCart(String email, int productId);

    Cart findCartByUser(Users user);
}
