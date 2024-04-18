package com.trantring.ecommerce.DTO.Response;

import com.trantring.ecommerce.DTO.CategoryDTO;
import com.trantring.ecommerce.Entity.Category;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class CategoryPageResponseDTO {
    private List<CategoryDTO> categories = new ArrayList<>();
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public CategoryPageResponseDTO(Page<Category> categoryPage) {
        List<Category> categoryList = categoryPage.getContent();
        categoryList.forEach(category -> {
            categories.add(new CategoryDTO(category.getId(), category.getTitle(), category.getDescription()));
        });
        pageNumber = categoryPage.getNumber();
        pageSize = categoryPage.getSize();
        totalElements = categoryPage.getTotalElements();
        totalPages = categoryPage.getTotalPages();
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
