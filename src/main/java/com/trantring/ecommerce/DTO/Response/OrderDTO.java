package com.trantring.ecommerce.DTO.Response;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    private int id;
    private int userId;
    private int totalPrice;
    private String address;
    private String phoneNumber;
    private Date dateCreated;

    private String status;

    private List<OrderItemDTO> orderItems;

    public OrderDTO(int id, int userId, int totalPrice, String address, String phoneNumber,
                    Date dateCreated, String status, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.dateCreated = dateCreated;
        this.status = status;
        this.orderItems = orderItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
