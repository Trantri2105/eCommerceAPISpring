package com.trantring.ecommerce.mapper;

import com.trantring.ecommerce.dto.CategoryDTO;
import com.trantring.ecommerce.dto.response.CategoryPageResponseDTO;
import com.trantring.ecommerce.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO categoryToCategoryDTO(Category category);

    List<CategoryDTO> categoryPageToCategoryDTOList(Page<Category> categoryPage);

    @Mapping(target = "pageNumber", source = "categoryPage.number")
    @Mapping(target = "pageSize", source = "categoryPage.size")
    @Mapping(target = "categories", source = "categoryPage")
    CategoryPageResponseDTO categoryPageToCategoryPageResponseDTO(Page<Category> categoryPage);
}
