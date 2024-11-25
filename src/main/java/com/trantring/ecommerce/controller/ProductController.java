package com.trantring.ecommerce.controller;

import com.trantring.ecommerce.dto.ProductDTO;
import com.trantring.ecommerce.dto.request.RequestParamsDTO;
import com.trantring.ecommerce.dto.response.ProductPageResponseDTO;
import com.trantring.ecommerce.entity.Product;
import com.trantring.ecommerce.mapper.ProductMapper;
import com.trantring.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {
    private ProductService productService;
    private ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductDTO productDTO) {
        productService.createProduct(productDTO);
        return new ResponseEntity<>("Product created successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/public/product")
    public ResponseEntity<ProductPageResponseDTO> getAllProducts(RequestParamsDTO requestParamsDTO) {
        Page<Product> products = productService.getAllProducts(requestParamsDTO);
        return new ResponseEntity<>(productMapper.productPageToProductPageResponseDTO(products), HttpStatus.OK);
    }

    @GetMapping("/public/product/category/{categoryId}")
    public ResponseEntity<ProductPageResponseDTO> getAllProductsByCategory(@PathVariable int categoryId, RequestParamsDTO requestParamsDTO) {
        Page<Product> products = productService.getProductsByCategoryId(requestParamsDTO, categoryId);
        return new ResponseEntity<>(productMapper.productPageToProductPageResponseDTO(products), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @PatchMapping("/product/{productId}")
    public ResponseEntity<?> updateProductById(@PathVariable int productId, @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.updateProduct(productDTO, productId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable int productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }
}
