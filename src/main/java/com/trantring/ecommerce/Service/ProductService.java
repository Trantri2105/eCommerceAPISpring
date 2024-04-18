package com.trantring.ecommerce.Service;


import com.trantring.ecommerce.DTO.ProductDTO;
import com.trantring.ecommerce.DTO.Response.ProductPageResponseDTO;
import com.trantring.ecommerce.Entity.Product;

public interface ProductService {
    void createProduct(ProductDTO productDTO);

    ProductPageResponseDTO getAllProducts(int pageNumber, int pageSize, String sortBy, String sortOrder);

    ProductPageResponseDTO getProductsByCategoryId(int pageNumber, int pageSize, String sortBy, String sortOrder, int categoryId);

    ProductDTO updateProduct(ProductDTO productDTO, int productId);

    void deleteProductById(int productId);

    Product findProductById(int productId);
}
