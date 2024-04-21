package com.trantring.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    @Min(value = 1)
    private int quantity;

    @Column(name = "price")
    private int price;

    public CartItem(Cart cart, Product product, int quantity, int price) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

}
