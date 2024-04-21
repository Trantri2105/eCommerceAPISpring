package com.trantring.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserPageResponseDTO {
    private List<UserResponseDTO> users;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
