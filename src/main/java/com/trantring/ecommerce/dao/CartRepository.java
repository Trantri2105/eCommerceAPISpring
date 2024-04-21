package com.trantring.ecommerce.dao;

import com.trantring.ecommerce.entity.Cart;
import com.trantring.ecommerce.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUser(Users user);
}
