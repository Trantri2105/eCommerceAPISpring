package com.trantring.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderDTO {
    private int id;
    private int userId;
    private int totalPrice;
    private String address;
    private String phoneNumber;
    private Date dateCreated;
    private String status;
    private List<OrderItemDTO> orderItems;
}
