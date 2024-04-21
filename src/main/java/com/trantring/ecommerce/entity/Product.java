package com.trantring.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "color")
    private String color;

    @Column(name = "size")
    private String size;

    @Column(name = "price")
    private int price;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "stocks")
    private int stocks;

    public Product(String title, String description, Category category, String color, String size, int price, Date dateCreated, int stocks) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.color = color;
        this.size = size;
        this.price = price;
        this.dateCreated = dateCreated;
        this.stocks = stocks;
    }
}

