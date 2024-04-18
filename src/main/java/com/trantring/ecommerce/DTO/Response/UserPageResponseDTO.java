package com.trantring.ecommerce.DTO.Response;

import com.trantring.ecommerce.Entity.Users;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class UserPageResponseDTO {
    private List<UserResponseDTO> users = new ArrayList<>();
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public UserPageResponseDTO(Page<Users> usersPage) {
        List<Users> usersList = usersPage.getContent();
        usersList.forEach(user -> {
            users.add(new UserResponseDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName()));
        });
        pageNumber = usersPage.getNumber();
        pageSize = usersPage.getSize();
        totalElements = usersPage.getTotalElements();
        totalPages = usersPage.getTotalPages();
    }

    public List<UserResponseDTO> getUsers() {
        return users;
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
