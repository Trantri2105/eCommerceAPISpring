package com.trantring.ecommerce.Controller;

import com.trantring.ecommerce.DTO.Request.OrderRequestDTO;
import com.trantring.ecommerce.DTO.Request.OrderUpdateRequestDTO;
import com.trantring.ecommerce.DTO.Response.OrderPageDTO;
import com.trantring.ecommerce.Exception.APIException;
import com.trantring.ecommerce.Service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO, Authentication authentication, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        try {
            orderService.createOrder(authentication.getName(), orderRequestDTO);
            return new ResponseEntity<>("Order created successfully!", HttpStatus.OK);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_user')")
    @GetMapping("/order")
    public ResponseEntity<?> getAllOrderInformation(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder, Authentication authentication) {
        try {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin"))) {
                OrderPageDTO orderPageDTO = orderService.getAllOrder(pageNumber, pageSize, sortBy, sortOrder);
                return new ResponseEntity<>(orderPageDTO, HttpStatus.OK);
            } else {
                OrderPageDTO orderPageDTO = orderService.getAllOrderOfAnUser(pageNumber, pageSize, sortBy, sortOrder, authentication.getName());
                return new ResponseEntity<>(orderPageDTO, HttpStatus.OK);
            }
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @PatchMapping("/order/{orderId}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable int orderId, @RequestBody @Valid OrderUpdateRequestDTO orderUpdateRequestDTO,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        try {
            orderService.updateOrderStatus(orderId, orderUpdateRequestDTO.getStatus());
            return new ResponseEntity<>("Order updated successfully!", HttpStatus.OK);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable int orderId, Authentication authentication) {
        try {
            orderService.deleteOrder(orderId, authentication.getName());
            return new ResponseEntity<>("Order deleted successfully!", HttpStatus.OK);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
