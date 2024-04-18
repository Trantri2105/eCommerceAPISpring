package com.trantring.ecommerce.Service.implement;

import com.trantring.ecommerce.DAO.OrderRepository;
import com.trantring.ecommerce.DTO.Request.OrderRequestDTO;
import com.trantring.ecommerce.DTO.Response.OrderDTO;
import com.trantring.ecommerce.DTO.Response.OrderItemDTO;
import com.trantring.ecommerce.DTO.Response.OrderPageDTO;
import com.trantring.ecommerce.Entity.Cart;
import com.trantring.ecommerce.Entity.Order;
import com.trantring.ecommerce.Entity.OrderItem;
import com.trantring.ecommerce.Entity.Users;
import com.trantring.ecommerce.Exception.APIException;
import com.trantring.ecommerce.Service.CartService;
import com.trantring.ecommerce.Service.OrderService;
import com.trantring.ecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
    }

    @Override
    public void createOrder(String email, OrderRequestDTO orderRequestDTO) {
        Users user = userService.findUserByEmail(email);
        Cart cart = cartService.findCartByUser(user);
        if (cart.getCartItems().isEmpty()) throw new APIException("Cart is empty!");
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
        order.setDateCreated(new Date());
        order.setStatus("Created");
        orderRepository.save(order);
        cart.getCartItems().clear();
        cart.setTotalPrice(0);
        cartService.save(cart);
    }

    @Override
    public OrderPageDTO getAllOrder(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        if (!(new ArrayList<>(List.of("id", "totalPrice")).contains(sortBy))) {
            throw new APIException("sortBy is invalid!");
        }
        if (!(new ArrayList<>(List.of("asc", "desc")).contains(sortOrder))) {
            throw new APIException("sortOrder is invalid!");
        }
        Sort sort = sortOrder.equals("asc") ? Sort.by(sortBy) : Sort.by(sortBy).descending();
        Page<Order> orders = orderRepository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        List<OrderDTO> orderDTOList = getOrderDTOList(orders);
        return new OrderPageDTO(orders.getNumber(), orders.getSize(), orders.getTotalElements(), orders.getTotalPages(), orderDTOList);
    }

    @Override
    public OrderPageDTO getAllOrderOfAnUser(int pageNumber, int pageSize, String sortBy, String sortOrder, String email) {
        if (!(new ArrayList<>(List.of("id", "totalPrice")).contains(sortBy))) {
            throw new APIException("sortBy is invalid!");
        }
        if (!(new ArrayList<>(List.of("asc", "desc")).contains(sortOrder))) {
            throw new APIException("sortOrder is invalid!");
        }
        Users user = userService.findUserByEmail(email);
        Sort sort = sortOrder.equals("asc") ? Sort.by(sortBy) : Sort.by(sortBy).descending();
        Page<Order> orders = orderRepository.findAllByUser(user, PageRequest.of(pageNumber, pageSize, sort));
        List<OrderDTO> orderDTOList = getOrderDTOList(orders);
        return new OrderPageDTO(orders.getNumber(), orders.getSize(), orders.getTotalElements(), orders.getTotalPages(), orderDTOList);
    }

    @Override
    public void updateOrderStatus(int orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new APIException("Order not found!"));
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(int orderId, String email) {
        Users user = userService.findUserByEmail(email);
        Order order = orderRepository.findByIdAndUser(orderId, user).orElseThrow(() -> new APIException("Order not found!"));
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
