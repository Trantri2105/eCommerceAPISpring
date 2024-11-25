package com.trantring.ecommerce.controller;

import com.trantring.ecommerce.dto.CategoryDTO;
import com.trantring.ecommerce.dto.request.RequestParamsDTO;
import com.trantring.ecommerce.dto.response.CategoryPageResponseDTO;
import com.trantring.ecommerce.entity.Category;
import com.trantring.ecommerce.mapper.CategoryMapper;
import com.trantring.ecommerce.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CategoryController {
    private CategoryService categoryService;

    private CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @PostMapping("/category")
    public ResponseEntity<String> createCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>("Category created successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/public/category")
    public ResponseEntity<CategoryPageResponseDTO> getAllCategories(RequestParamsDTO requestParamsDTO) {
        Page<Category> categories = categoryService.getAllCategory(requestParamsDTO);
        return new ResponseEntity<>(categoryMapper.categoryPageToCategoryPageResponseDTO(categories), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @PatchMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategoryById(@RequestBody CategoryDTO categoryDTO, @PathVariable int categoryId) {
        Category category = categoryService.updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(categoryMapper.categoryToCategoryDTO(category), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable int categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category deleted successfully!", HttpStatus.OK);
    }
}
