package com.trantring.ecommerce.service.implement;

import com.trantring.ecommerce.dao.OrderRepository;
import com.trantring.ecommerce.dto.request.OrderRequestDTO;
import com.trantring.ecommerce.dto.request.RequestParamsDTO;
import com.trantring.ecommerce.dto.response.OrderDTO;
import com.trantring.ecommerce.dto.response.OrderItemDTO;
import com.trantring.ecommerce.entity.Cart;
import com.trantring.ecommerce.entity.Order;
import com.trantring.ecommerce.entity.OrderItem;
import com.trantring.ecommerce.entity.Users;
import com.trantring.ecommerce.service.CartService;
import com.trantring.ecommerce.service.OrderService;
import com.trantring.ecommerce.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private UserService userService;

    private CartService cartService;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
    }

    @Override
    public void createOrder(String email, OrderRequestDTO orderRequestDTO) {
        Users user = userService.findUserByEmail(email);
        Cart cart = cartService.findCartByUser(user);
        if (cart.getCartItems().isEmpty()) throw new RuntimeException("Cart is empty!");
        List<OrderItem> orderItems = new ArrayList<>();
        Order order = new Order();
        cart.getCartItems().forEach(cartItem -> {
            orderItems.add(new OrderItem(order, cartItem.getProduct(), cartItem.getQuantity(), cartItem.getPrice()));
        });
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setTotalPrice(cart.getTotalPrice());
        order.setAddress(orderRequestDTO.getAddress());
        order.setPhoneNumber(orderRequestDTO.getPhoneNumber());
        order.setDateCreated(new Date(System.currentTimeMillis()));
        order.setStatus("Created");
        orderRepository.save(order);
        cart.getCartItems().clear();
        cart.setTotalPrice(0);
        cartService.save(cart);
    }

    @Override
    public Page<Order> getAllOrder(RequestParamsDTO requestParamsDTO) {
        if (requestParamsDTO.getPageSize() > 20) requestParamsDTO.setPageSize(20);
        if (!(new ArrayList<>(List.of("id", "totalPrice")).contains(requestParamsDTO.getSortBy()))) {
            throw new RuntimeException("sortBy is invalid!");
        }
        if (!(new ArrayList<>(List.of("asc", "desc")).contains(requestParamsDTO.getSortOrder()))) {
            throw new RuntimeException("sortOrder is invalid!");
        }
        Sort sort = requestParamsDTO.getSortOrder().equals("asc") ? Sort.by(requestParamsDTO.getSortBy()) : Sort.by(requestParamsDTO.getSortBy()).descending();
        return orderRepository.findAll(PageRequest.of(requestParamsDTO.getPageNumber(), requestParamsDTO.getPageSize(), sort));
    }

    @Override
    public Page<Order> getAllOrderOfAnUser(RequestParamsDTO requestParamsDTO, String email) {
        if (requestParamsDTO.getPageSize() > 20) requestParamsDTO.setPageSize(20);
        if (!(new ArrayList<>(List.of("id", "totalPrice")).contains(requestParamsDTO.getSortBy()))) {
            throw new RuntimeException("sortBy is invalid!");
        }
        if (!(new ArrayList<>(List.of("asc", "desc")).contains(requestParamsDTO.getSortOrder()))) {
            throw new RuntimeException("sortOrder is invalid!");
        }
        Users user = userService.findUserByEmail(email);
        Sort sort = requestParamsDTO.getSortOrder().equals("asc") ? Sort.by(requestParamsDTO.getSortBy()) : Sort.by(requestParamsDTO.getSortBy()).descending();
        return orderRepository.findAllByUser(user, PageRequest.of(requestParamsDTO.getPageNumber(), requestParamsDTO.getPageSize(), sort));
    }

    @Override
    public void updateOrderStatus(int orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found!"));
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(int orderId, String email) {
        Users user = userService.findUserByEmail(email);
        Order order = orderRepository.findByIdAndUser(orderId, user).orElseThrow(() -> new RuntimeException("Order not found!"));
        orderRepository.delete(order);
    }

    private List<OrderDTO> getOrderDTOList(Page<Order> orders) {
        List<Order> orderList = orders.getContent();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderList.forEach(order -> {
            List<OrderItem> orderItems = order.getOrderItems();
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
            orderItems.forEach(item -> {
                orderItemDTOList.add(new OrderItemDTO(item.getProduct().getId(), item.getQuantity(), item.getPrice()));
            });
            orderDTOList.add(new OrderDTO(order.getId(), order.getUser().getId(), order.getTotalPrice(),
                    order.getAddress(), order.getPhoneNumber(), order.getDateCreated(), order.getStatus(), orderItemDTOList));
        });
        return orderDTOList;
    }
}
