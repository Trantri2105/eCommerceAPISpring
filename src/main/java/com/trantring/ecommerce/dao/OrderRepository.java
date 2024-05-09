package com.trantring.ecommerce.dao;

import com.trantring.ecommerce.entity.Order;
import com.trantring.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findAllByUser(User user, Pageable pageable);

    Optional<Order> findByIdAndUser(int id, User user);
}
