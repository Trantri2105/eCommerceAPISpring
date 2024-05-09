package com.trantring.ecommerce.service;

import com.trantring.ecommerce.dto.request.LoginDTO;
import com.trantring.ecommerce.dto.request.RegisterDTO;
import com.trantring.ecommerce.dto.request.RequestParamsDTO;
import com.trantring.ecommerce.dto.request.UserRequestDTO;
import com.trantring.ecommerce.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
    User save(User user);

    void registerUser(RegisterDTO registerDTO);

    User findUserByEmail(String email);

    User findUserById(int userId);

    Page<User> getAllUser(RequestParamsDTO requestParamsDTO);

    User updateUser(UserRequestDTO userRequestDTO, String email);

    void deleteUser(int userId);

    String login(LoginDTO loginDTO);
}
