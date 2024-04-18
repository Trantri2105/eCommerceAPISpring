package com.trantring.ecommerce.Service;

import com.trantring.ecommerce.DTO.Request.RegisterDTO;
import com.trantring.ecommerce.DTO.Response.UserPageResponseDTO;
import com.trantring.ecommerce.DTO.Request.UserRequestDTO;
import com.trantring.ecommerce.DTO.Response.UserResponseDTO;
import com.trantring.ecommerce.Entity.Users;

public interface UserService {
    Users save(Users user);

    void registerUser(RegisterDTO registerDTO);

    Users findUserByEmail(String email);

    Users findUserById(int userId);

    UserPageResponseDTO getAllUser(int pageNumber, int pageSize, String sortBy, String sortOrder);

    UserResponseDTO updateUser(UserRequestDTO userRequestDTO, String email);

    void deleteUser(int userId);
}
