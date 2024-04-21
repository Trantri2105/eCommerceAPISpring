package com.trantring.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {
    private int id;

    @NotBlank(message = "Title is required!")
    private String title;
    private String description;

    @NotNull(message = "Category id is required!")
    private Integer categoryId;

    @NotNull(message = "Color is required!")
    private String color;

    @NotNull(message = "Size is required!")
    private String size;

    @NotNull(message = "Price is required")
    private Integer price;

    private Date dateCreated;

    @NotNull(message = "Stocks is required")
    private Integer stocks;
}
