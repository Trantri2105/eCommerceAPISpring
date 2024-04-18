package com.trantring.ecommerce.Controller;

import com.trantring.ecommerce.DTO.ProductDTO;
import com.trantring.ecommerce.DTO.Response.ProductPageResponseDTO;
import com.trantring.ecommerce.Exception.APIException;
import com.trantring.ecommerce.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductDTO productDTO,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        productService.createProduct(productDTO);
        return new ResponseEntity<>("Product created successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/public/product")
    public ResponseEntity<?> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder) {
        try {
            ProductPageResponseDTO productPageResponseDTO = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
            return new ResponseEntity<>(productPageResponseDTO, HttpStatus.OK);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/public/product/category/{categoryId}")
    public ResponseEntity<?> getAllProductsByCategory(
            @PathVariable int categoryId,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder) {
        try {
            ProductPageResponseDTO productPageResponseDTO = productService.getProductsByCategoryId(pageNumber, pageSize, sortBy, sortOrder, categoryId);
            return new ResponseEntity<>(productPageResponseDTO, HttpStatus.OK);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @PatchMapping("/product/{productId}")
    public ResponseEntity<?> updateProductById(@PathVariable int productId, @RequestBody ProductDTO productDTO) {
        try {
            return new ResponseEntity<>(productService.updateProduct(productDTO, productId), HttpStatus.OK);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable int productId) {
        try {
            productService.deleteProductById(productId);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
