package com.trantring.ecommerce.DTO.Response;

import com.trantring.ecommerce.DTO.ProductDTO;
import com.trantring.ecommerce.Entity.Product;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class ProductPageResponseDTO {
    private List<ProductDTO> products = new ArrayList<>();
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public ProductPageResponseDTO(Page<Product> productsPage) {
        List<Product> productList = productsPage.getContent();
        productList.forEach(product -> {
            products.add(new ProductDTO(product.getId(), product.getTitle(),
                    product.getDescription(), product.getCategory().getId(),
                    product.getColor(), product.getSize(), product.getPrice(),
                    product.getDateCreated(), product.getStocks()));
        });
        pageNumber = productsPage.getNumber();
        pageSize = productsPage.getSize();
        totalElements = productsPage.getTotalElements();
        totalPages = productsPage.getTotalPages();
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

}
