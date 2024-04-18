package com.trantring.ecommerce.Controller;

import com.trantring.ecommerce.DTO.CategoryDTO;
import com.trantring.ecommerce.DTO.Response.CategoryPageResponseDTO;
import com.trantring.ecommerce.Exception.APIException;
import com.trantring.ecommerce.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @PostMapping("/category")
    public ResponseEntity<String> createCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>("Category created successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/public/category")
    public ResponseEntity<?> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder) {
        try {
            CategoryPageResponseDTO categoryPageResponseDTO = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortOrder);
            return new ResponseEntity<>(categoryPageResponseDTO, HttpStatus.OK);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @PatchMapping("/category/{categoryId}")
    public ResponseEntity<?> updateCategoryById(@RequestBody CategoryDTO categoryDTO,
                                                @PathVariable int categoryId) {
        try {
            CategoryDTO categoryResponseDTO = categoryService.updateCategory(categoryDTO, categoryId);
            return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable int categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>("Category deleted successfully!", HttpStatus.OK);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
