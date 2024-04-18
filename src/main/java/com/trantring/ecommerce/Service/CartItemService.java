package com.trantring.ecommerce.Service;

import com.trantring.ecommerce.Entity.Cart;
import com.trantring.ecommerce.Entity.CartItem;
import com.trantring.ecommerce.Entity.Product;

import java.util.Optional;

public interface CartItemService {
    Optional<CartItem> findCartItemByCartAndProduct(Cart cart, Product product);

    void delete(CartItem cartItem);
}
