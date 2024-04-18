package com.trantring.ecommerce.DAO;

import com.trantring.ecommerce.Entity.Cart;
import com.trantring.ecommerce.Entity.CartItem;
import com.trantring.ecommerce.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
