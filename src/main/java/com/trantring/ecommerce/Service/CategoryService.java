package com.trantring.ecommerce.Service;

import com.trantring.ecommerce.DTO.CategoryDTO;
import com.trantring.ecommerce.DTO.Response.CategoryPageResponseDTO;
import com.trantring.ecommerce.Entity.Category;

public interface CategoryService {
    void createCategory(CategoryDTO categoryDTO);

    CategoryPageResponseDTO getAllCategory(int pageNumber, int pageSize, String sortBy, String sortOrder);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, int categoryId);

    void deleteCategory(int categoryId);

    Category findByCategoryId(int categoryId);
}
