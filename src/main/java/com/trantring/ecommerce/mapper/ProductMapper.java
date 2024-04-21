package com.trantring.ecommerce.mapper;

import com.trantring.ecommerce.dto.ProductDTO;
import com.trantring.ecommerce.dto.response.ProductPageResponseDTO;
import com.trantring.ecommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryId", source = "product.category.id")
    ProductDTO productToProductDTO(Product product);

    List<ProductDTO> productPageToProductDTOList(Page<Product> productPage);

    @Mapping(target = "pageNumber", source = "productPage.number")
    @Mapping(target = "pageSize", source = "productPage.size")
    @Mapping(target = "products", source = "productPage")
    ProductPageResponseDTO productPageToProductPageResponseDTO(Page<Product> productPage);
}
