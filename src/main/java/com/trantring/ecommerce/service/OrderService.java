package com.trantring.ecommerce.service;

import com.trantring.ecommerce.dto.request.OrderRequestDTO;
import com.trantring.ecommerce.dto.request.RequestParamsDTO;
import com.trantring.ecommerce.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {
    void createOrder(String email, OrderRequestDTO orderRequestDTO);

    Page<Order> getAllOrder(RequestParamsDTO requestParamsDTO);

    Page<Order> getAllOrderOfAnUser(RequestParamsDTO requestParamsDTO, String email);

    void updateOrderStatus(int orderId, String status);

    void deleteOrder(int orderId, String email);
}
