package com.trantring.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderPageDTO {
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private List<OrderDTO> orders;
}
