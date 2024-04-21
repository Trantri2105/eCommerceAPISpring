package com.trantring.ecommerce.controller;

import com.trantring.ecommerce.dto.request.OrderRequestDTO;
import com.trantring.ecommerce.dto.request.OrderUpdateRequestDTO;
import com.trantring.ecommerce.dto.request.RequestParamsDTO;
import com.trantring.ecommerce.dto.response.OrderPageDTO;
import com.trantring.ecommerce.entity.Order;
import com.trantring.ecommerce.mapper.OrderMapper;
import com.trantring.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {
    private OrderService orderService;

    private OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO, Authentication authentication) {
        orderService.createOrder(authentication.getName(), orderRequestDTO);
        return new ResponseEntity<>("Order created successfully!", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_user')")
    @GetMapping("/order")
    public ResponseEntity<OrderPageDTO> getAllOrderInformation(RequestParamsDTO requestParamsDTO, Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin"))) {
            Page<Order> orderPage = orderService.getAllOrder(requestParamsDTO);
            return new ResponseEntity<>(orderMapper.orderPageToOrderPageDTO(orderPage), HttpStatus.OK);
        } else {
            Page<Order> orderPage = orderService.getAllOrderOfAnUser(requestParamsDTO, authentication.getName());
            return new ResponseEntity<>(orderMapper.orderPageToOrderPageDTO(orderPage), HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @PatchMapping("/order/{orderId}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable int orderId, @RequestBody @Valid OrderUpdateRequestDTO orderUpdateRequestDTO) {
        orderService.updateOrderStatus(orderId, orderUpdateRequestDTO.getStatus());
        return new ResponseEntity<>("Order updated successfully!", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable int orderId, Authentication authentication) {
        orderService.deleteOrder(orderId, authentication.getName());
        return new ResponseEntity<>("Order deleted successfully!", HttpStatus.OK);
    }
}
