package com.trantring.ecommerce.service.implement;

import com.trantring.ecommerce.dao.ProductRepository;
import com.trantring.ecommerce.dto.ProductDTO;
import com.trantring.ecommerce.dto.request.RequestParamsDTO;
import com.trantring.ecommerce.entity.Category;
import com.trantring.ecommerce.entity.Product;
import com.trantring.ecommerce.service.CategoryService;
import com.trantring.ecommerce.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public void createProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO.getTitle(), productDTO.getDescription(),
                categoryService.findByCategoryId(productDTO.getCategoryId()),
                productDTO.getColor(), productDTO.getSize(), productDTO.getPrice(),
                new Date(), productDTO.getStocks());
        productRepository.save(product);
    }

    @Override
    public Page<Product> getAllProducts(RequestParamsDTO requestParamsDTO) {
        if (requestParamsDTO.getPageSize() > 20) requestParamsDTO.setPageSize(20);
        if (!(new ArrayList<>(List.of("id", "title", "price")).contains(requestParamsDTO.getSortBy()))) {
            throw new RuntimeException("sortBy is invalid!");
        }
        if (!(new ArrayList<>(List.of("asc", "desc")).contains(requestParamsDTO.getSortOrder()))) {
            throw new RuntimeException("sortOrder is invalid!");
        }
        Sort sort = requestParamsDTO.getSortOrder().equals("asc") ? Sort.by(requestParamsDTO.getSortBy()) : Sort.by(requestParamsDTO.getSortBy()).descending();
        return productRepository.findAll(PageRequest.of(requestParamsDTO.getPageNumber(), requestParamsDTO.getPageSize(), sort));
    }

    @Override
    public Page<Product> getProductsByCategoryId(RequestParamsDTO requestParamsDTO, int categoryId) {
        if (requestParamsDTO.getPageSize() > 20) requestParamsDTO.setPageSize(20);
        if (!(new ArrayList<>(List.of("id", "title", "price")).contains(requestParamsDTO.getSortBy()))) {
            throw new RuntimeException("sortBy is invalid!");
        }
        if (!(new ArrayList<>(List.of("asc", "desc")).contains(requestParamsDTO.getSortOrder()))) {
            throw new RuntimeException("sortOrder is invalid!");
        }
        Category category = categoryService.findByCategoryId(categoryId);
        Sort sort = requestParamsDTO.getSortOrder().equals("asc") ? Sort.by(requestParamsDTO.getSortBy()) : Sort.by(requestParamsDTO.getSortBy()).descending();
        return productRepository.findAllByCategory(category, PageRequest.of(requestParamsDTO.getPageNumber(), requestParamsDTO.getPageSize(), sort));
    }

    @Override
    public Product updateProduct(ProductDTO productDTO, int productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found!"));
        if (productDTO.getTitle() != null) product.setTitle(productDTO.getTitle());
        if (productDTO.getDescription() != null) product.setDescription(productDTO.getDescription());
        if (productDTO.getCategoryId() != null)
            product.setCategory(categoryService.findByCategoryId(productDTO.getCategoryId()));
        if (productDTO.getColor() != null) product.setColor(productDTO.getColor());
        if (productDTO.getSize() != null) product.setSize(productDTO.getSize());
        if (productDTO.getPrice() != null) product.setPrice(productDTO.getPrice());
        if (productDTO.getStocks() != null) product.setStocks(productDTO.getStocks());
        return productRepository.save(product);
    }

    @Override
    public void deleteProductById(int productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found!"));
        productRepository.delete(product);
    }

    @Override
    public Product findProductById(int productId) {
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found!"));
    }
}
