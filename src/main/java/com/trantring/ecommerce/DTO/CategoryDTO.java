package com.trantring.ecommerce.DTO;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {
    private int id;

    @NotBlank(message = "Title is required")
    private String title;
    private String description;

    public CategoryDTO(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
