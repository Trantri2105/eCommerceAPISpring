package com.trantring.ecommerce.service.implement;

import com.trantring.ecommerce.dao.CategoryRepository;
import com.trantring.ecommerce.dto.CategoryDTO;
import com.trantring.ecommerce.dto.request.RequestParamsDTO;
import com.trantring.ecommerce.entity.Category;
import com.trantring.ecommerce.service.CategoryService;
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
    public Page<Category> getAllCategory(RequestParamsDTO requestParamsDTO) {
        if (requestParamsDTO.getPageSize() > 20) requestParamsDTO.setPageSize(20);
        if (!(new ArrayList<>(List.of("id", "title")).contains(requestParamsDTO.getSortBy()))) {
            throw new RuntimeException("sortBy is invalid!");
        }
        if (!(new ArrayList<>(List.of("asc", "desc")).contains(requestParamsDTO.getSortOrder()))) {
            throw new RuntimeException("sortOrder is invalid!");
        }
        Sort sort = requestParamsDTO.getSortOrder().equals("asc") ? Sort.by(requestParamsDTO.getSortBy()) : Sort.by(requestParamsDTO.getSortBy()).descending();
        return categoryRepository.findAll(PageRequest.of(requestParamsDTO.getPageNumber(), requestParamsDTO.getPageSize(), sort));
    }

    @Override
    public Category updateCategory(CategoryDTO categoryDTO, int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found!"));
        if (categoryDTO.getTitle() != null) {
            category.setTitle(categoryDTO.getTitle());
        }
        if (categoryDTO.getDescription() != null) {
            category.setDescription(categoryDTO.getDescription());
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found!"));
        categoryRepository.delete(category);
    }

    @Override
    public Category findByCategoryId(int categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category Not Found!"));
    }
}
