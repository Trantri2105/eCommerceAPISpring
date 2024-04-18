package com.trantring.ecommerce.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class ProductDTO {
    private int id;

    @NotBlank(message = "Title is required")
    private String title;
    private String description;

    @NotNull(message = "Category id is required")
    private Integer categoryId;

    @NotNull(message = "Color is required")
    private String color;

    @NotNull(message = "Size is required")
    private String size;

    @NotNull(message = "Price is required")
    private Integer price;

    private Date dateCreated;

    @NotNull(message = "Stocks is required")
    private Integer stocks;

    public ProductDTO(int id, String title, String description, Integer categoryId, String color, String size, Integer price, Date dateCreated, Integer stocks) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.color = color;
        this.size = size;
        this.price = price;
        this.dateCreated = dateCreated;
        this.stocks = stocks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getStocks() {
        return stocks;
    }

    public void setStocks(Integer stocks) {
        this.stocks = stocks;
    }
}
