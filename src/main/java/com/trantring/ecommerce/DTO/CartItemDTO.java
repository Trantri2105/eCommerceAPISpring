package com.trantring.ecommerce.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartItemDTO {

    @NotNull(message = "Product id is required!")
    private Integer productId;

    @NotNull(message = "Quantity is required!")
    @Min(value = 1, message = "Quantity must greater than 0!")
    private Integer quantity;

    private int price;

    public CartItemDTO(int productId, int quantity, int price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
