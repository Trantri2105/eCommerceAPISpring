package com.trantring.ecommerce.mapper;

import com.trantring.ecommerce.dto.response.OrderDTO;
import com.trantring.ecommerce.dto.response.OrderItemDTO;
import com.trantring.ecommerce.dto.response.OrderPageDTO;
import com.trantring.ecommerce.entity.Order;
import com.trantring.ecommerce.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "productId", source = "orderItem.product.id")
    OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem);

    @Mapping(target = "userId", source = "order.user.id")
    OrderDTO orderToOrderDTO(Order order);

    List<OrderDTO> orderPageToOrderDTOList(Page<Order> orderPage);

    @Mapping(target = "pageNumber", source = "orderPage.number")
    @Mapping(target = "pageSize", source = "orderPage.size")
    @Mapping(target = "orders", source = "orderPage")
    OrderPageDTO orderPageToOrderPageDTO(Page<Order> orderPage);
}
