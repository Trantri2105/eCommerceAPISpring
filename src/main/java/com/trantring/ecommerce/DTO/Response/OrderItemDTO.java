package com.trantring.ecommerce.DTO.Response;

public class OrderItemDTO {
    private int product_id;
    private int quantity;
    private int price;

    public OrderItemDTO(int product_id, int quantity, int price) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
