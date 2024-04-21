package com.trantring.ecommerce.service;

import com.trantring.ecommerce.entity.Cart;
import com.trantring.ecommerce.entity.CartItem;
import com.trantring.ecommerce.entity.Product;

import java.util.Optional;

public interface CartItemService {
    Optional<CartItem> findCartItemByCartAndProduct(Cart cart, Product product);

    void delete(CartItem cartItem);
}
