package com.trantring.ecommerce.service;

import com.trantring.ecommerce.dto.CategoryDTO;
import com.trantring.ecommerce.dto.request.RequestParamsDTO;
import com.trantring.ecommerce.entity.Category;
import org.springframework.data.domain.Page;

public interface CategoryService {
    void createCategory(CategoryDTO categoryDTO);

    Page<Category> getAllCategory(RequestParamsDTO requestParamsDTO);

    Category updateCategory(CategoryDTO categoryDTO, int categoryId);

    void deleteCategory(int categoryId);

    Category findByCategoryId(int categoryId);
}
