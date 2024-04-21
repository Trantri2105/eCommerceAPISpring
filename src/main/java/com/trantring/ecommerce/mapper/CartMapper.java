package com.trantring.ecommerce.mapper;

import com.trantring.ecommerce.dto.CartItemDTO;
import com.trantring.ecommerce.dto.response.CartResponseDTO;
import com.trantring.ecommerce.entity.Cart;
import com.trantring.ecommerce.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "productId", source = "cartItem.product.id")
    CartItemDTO cartItemToCartItemDTO(CartItem cartItem);

    CartResponseDTO cartToCartResponseDTO(Cart cart);
}
