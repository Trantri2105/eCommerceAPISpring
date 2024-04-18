package com.trantring.ecommerce.Service;

import com.trantring.ecommerce.DTO.Request.OrderRequestDTO;
import com.trantring.ecommerce.DTO.Response.OrderPageDTO;

public interface OrderService {
    void createOrder(String email, OrderRequestDTO orderRequestDTO);

    OrderPageDTO getAllOrder(int pageNumber, int pageSize, String sortBy, String sortOrder);

    OrderPageDTO getAllOrderOfAnUser(int pageNumber, int pageSize, String sortBy, String sortOrder, String email);

    void updateOrderStatus(int orderId, String status);

    void deleteOrder(int orderId, String email);
}
