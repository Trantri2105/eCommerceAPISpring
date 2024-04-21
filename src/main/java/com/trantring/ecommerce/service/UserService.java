package com.trantring.ecommerce.service;

import com.trantring.ecommerce.dto.request.LoginDTO;
import com.trantring.ecommerce.dto.request.RegisterDTO;
import com.trantring.ecommerce.dto.request.RequestParamsDTO;
import com.trantring.ecommerce.dto.request.UserRequestDTO;
import com.trantring.ecommerce.entity.Users;
import org.springframework.data.domain.Page;

public interface UserService {
    Users save(Users user);

    void registerUser(RegisterDTO registerDTO);

    Users findUserByEmail(String email);

    Users findUserById(int userId);

    Page<Users> getAllUser(RequestParamsDTO requestParamsDTO);

    Users updateUser(UserRequestDTO userRequestDTO, String email);

    void deleteUser(int userId);

    String login(LoginDTO loginDTO);
}
