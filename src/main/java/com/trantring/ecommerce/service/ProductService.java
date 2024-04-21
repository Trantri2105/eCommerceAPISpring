package com.trantring.ecommerce.service;


import com.trantring.ecommerce.dto.ProductDTO;
import com.trantring.ecommerce.dto.request.RequestParamsDTO;
import com.trantring.ecommerce.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
    void createProduct(ProductDTO productDTO);

    Page<Product> getAllProducts(RequestParamsDTO requestParamsDTO);

    Page<Product> getProductsByCategoryId(RequestParamsDTO requestParamsDTO, int categoryId);

    Product updateProduct(ProductDTO productDTO, int productId);

    void deleteProductById(int productId);

    Product findProductById(int productId);
}
