package com.trantring.ecommerce.dto.response;

import com.trantring.ecommerce.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryPageResponseDTO {
    private List<CategoryDTO> categories;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
