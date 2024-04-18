package com.trantring.ecommerce.DAO;

import com.trantring.ecommerce.Entity.Order;
import com.trantring.ecommerce.Entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findAllByUser(Users user, Pageable pageable);

    Optional<Order> findByIdAndUser(int id, Users user);
}
