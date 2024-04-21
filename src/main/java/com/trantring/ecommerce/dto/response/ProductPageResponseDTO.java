package com.trantring.ecommerce.dto.response;

import com.trantring.ecommerce.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductPageResponseDTO {
    private List<ProductDTO> products;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

}
