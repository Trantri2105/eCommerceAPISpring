package com.trantring.ecommerce.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestParamsDTO {
    private int pageNumber = 0;
    private int pageSize = 10;
    private String sortBy = "id";
    private String sortOrder = "asc";
}
