package com.trantring.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryDTO {
    private int id;

    @NotBlank(message = "Title is required!")
    private String title;
    private String description;
}
