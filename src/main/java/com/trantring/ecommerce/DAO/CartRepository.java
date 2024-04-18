package com.trantring.ecommerce.DAO;

import com.trantring.ecommerce.Entity.Cart;
import com.trantring.ecommerce.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUser(Users user);
}
