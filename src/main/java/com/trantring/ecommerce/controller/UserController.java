package com.trantring.ecommerce.controller;

import com.trantring.ecommerce.dto.request.RequestParamsDTO;
import com.trantring.ecommerce.dto.request.UserRequestDTO;
import com.trantring.ecommerce.dto.response.UserPageResponseDTO;
import com.trantring.ecommerce.dto.response.UserResponseDTO;
import com.trantring.ecommerce.entity.Users;
import com.trantring.ecommerce.mapper.UserMapper;
import com.trantring.ecommerce.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;

    private UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PreAuthorize("hasAnyRole('ROLE_user','ROLE_admin')")
    @GetMapping("/user/info")
    public ResponseEntity<UserResponseDTO> getUserInformation(Authentication authentication) {
        Users user = userService.findUserByEmail(authentication.getName());
        return new ResponseEntity<>(userMapper.userToUserResponseDTO(user), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @GetMapping("/user")
    public ResponseEntity<UserPageResponseDTO> getAllUserInformation(RequestParamsDTO requestParamsDTO) {
        Page<Users> usersPage = userService.getAllUser(requestParamsDTO);
        return new ResponseEntity<>(userMapper.userPageToUserPageResponseDTO(usersPage), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_user','ROLE_admin')")
    @PatchMapping("/user")
    public ResponseEntity<UserResponseDTO> updateUserInformation(@RequestBody UserRequestDTO userRequestDTO, Authentication authentication) {
        Users updateUser = userService.updateUser(userRequestDTO, authentication.getName());
        return new ResponseEntity<>(userMapper.userToUserResponseDTO(updateUser), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable int userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
    }
}
