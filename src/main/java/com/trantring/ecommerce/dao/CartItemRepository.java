package com.trantring.ecommerce.dao;

import com.trantring.ecommerce.entity.Cart;
import com.trantring.ecommerce.entity.CartItem;
import com.trantring.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
