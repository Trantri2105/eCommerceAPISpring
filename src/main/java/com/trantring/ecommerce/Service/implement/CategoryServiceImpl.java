package com.trantring.ecommerce.Service.implement;

import com.trantring.ecommerce.DAO.CategoryRepository;
import com.trantring.ecommerce.DTO.CategoryDTO;
import com.trantring.ecommerce.DTO.Response.CategoryPageResponseDTO;
import com.trantring.ecommerce.Entity.Category;
import com.trantring.ecommerce.Exception.APIException;
import com.trantring.ecommerce.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setTitle(categoryDTO.getTitle());
        category.setDescription(categoryDTO.getDescription());
        categoryRepository.save(category);
    }

    @Override
    public CategoryPageResponseDTO getAllCategory(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        if (!(new ArrayList<>(List.of("id", "title")).contains(sortBy))) {
            throw new APIException("sortBy is invalid!");
        }
        if (!(new ArrayList<>(List.of("asc", "desc")).contains(sortOrder))) {
            throw new APIException("sortOrder is invalid!");
        }
        Sort sort = sortOrder.equals("asc") ? Sort.by(sortBy) : Sort.by(sortBy).descending();
        Page<Category> categories = categoryRepository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        return new CategoryPageResponseDTO(categories);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new APIException("Category not found!"));
        if (categoryDTO.getTitle() != null) {
            category.setTitle(categoryDTO.getTitle());
        }
        if (categoryDTO.getDescription() != null) {
            category.setDescription(categoryDTO.getDescription());
        }
        Category newCategory = categoryRepository.save(category);
        return new CategoryDTO(newCategory.getId(), newCategory.getTitle(), newCategory.getDescription());
    }

    @Override
    public void deleteCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new APIException("Category not found!"));
        categoryRepository.delete(category);
    }

    @Override
    public Category findByCategoryId(int categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new APIException("Category Not Found!"));
    }
}
