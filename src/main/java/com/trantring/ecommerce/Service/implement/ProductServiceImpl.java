package com.trantring.ecommerce.Service.implement;

import com.trantring.ecommerce.DAO.ProductRepository;
import com.trantring.ecommerce.DTO.ProductDTO;
import com.trantring.ecommerce.DTO.Response.ProductPageResponseDTO;
import com.trantring.ecommerce.Entity.Category;
import com.trantring.ecommerce.Entity.Product;
import com.trantring.ecommerce.Exception.APIException;
import com.trantring.ecommerce.Service.CategoryService;
import com.trantring.ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
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
    public ProductPageResponseDTO getAllProducts(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        if (!(new ArrayList<>(List.of("id", "title", "price")).contains(sortBy))) {
            throw new APIException("sortBy is invalid!");
        }
        if (!(new ArrayList<>(List.of("asc", "desc")).contains(sortOrder))) {
            throw new APIException("sortOrder is invalid!");
        }
        Sort sort = sortOrder.equals("asc") ? Sort.by(sortBy) : Sort.by(sortBy).descending();
        Page<Product> products = productRepository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        return new ProductPageResponseDTO(products);
    }

    @Override
    public ProductPageResponseDTO getProductsByCategoryId(int pageNumber, int pageSize, String sortBy, String sortOrder, int categoryId) {
        if (!(new ArrayList<>(List.of("id", "title", "price")).contains(sortBy))) {
            throw new APIException("sortBy is invalid!");
        }
        if (!(new ArrayList<>(List.of("asc", "desc")).contains(sortOrder))) {
            throw new APIException("sortOrder is invalid!");
        }
        Category category = categoryService.findByCategoryId(categoryId);
        Sort sort = sortOrder.equals("asc") ? Sort.by(sortBy) : Sort.by(sortBy).descending();
        Page<Product> products = productRepository.findAllByCategory(category, PageRequest.of(pageNumber, pageSize, sort));
        return new ProductPageResponseDTO(products);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, int productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new APIException("Product not found!"));
        if (productDTO.getTitle() != null) product.setTitle(productDTO.getTitle());
        if (productDTO.getDescription() != null) product.setDescription(productDTO.getDescription());
        if (productDTO.getCategoryId() != null)
            product.setCategory(categoryService.findByCategoryId(productDTO.getCategoryId()));
        if (productDTO.getColor() != null) product.setColor(productDTO.getColor());
        if (productDTO.getSize() != null) product.setSize(productDTO.getSize());
        if (productDTO.getPrice() != null) product.setPrice(productDTO.getPrice());
        if (productDTO.getStocks() != null) product.setStocks(productDTO.getStocks());
        Product updatedProduct = productRepository.save(product);
        return new ProductDTO(updatedProduct.getId(), updatedProduct.getTitle(), updatedProduct.getDescription(),
                updatedProduct.getCategory().getId(), updatedProduct.getColor(), updatedProduct.getSize(),
                updatedProduct.getPrice(), updatedProduct.getDateCreated(), updatedProduct.getStocks());
    }

    @Override
    public void deleteProductById(int productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new APIException("Product not found!"));
        productRepository.delete(product);
    }

    @Override
    public Product findProductById(int productId) {
        return productRepository.findById(productId).orElseThrow(() -> new APIException("Product not found!"));
    }
}
