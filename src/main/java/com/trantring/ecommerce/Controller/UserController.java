package com.trantring.ecommerce.Controller;

import com.trantring.ecommerce.DTO.Response.UserPageResponseDTO;
import com.trantring.ecommerce.DTO.Request.UserRequestDTO;
import com.trantring.ecommerce.DTO.Response.UserResponseDTO;
import com.trantring.ecommerce.Entity.Users;
import com.trantring.ecommerce.Exception.APIException;
import com.trantring.ecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ROLE_user','ROLE_admin')")
    @GetMapping("/user/info")
    public ResponseEntity<?> getUserInformation(Authentication authentication) {
        try {
            Users user = userService.findUserByEmail(authentication.getName());
            return new ResponseEntity<>(new UserResponseDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName()), HttpStatus.OK);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @GetMapping("/user")
    public ResponseEntity<?> getAllUserInformation(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder) {
        try {
            UserPageResponseDTO userPageResponseDTO = userService.getAllUser(pageNumber, pageSize, sortBy, sortOrder);
            return new ResponseEntity<>(userPageResponseDTO, HttpStatus.OK);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_user','ROLE_admin')")
    @PatchMapping("/user")
    public ResponseEntity<?> updateUserInformation(@RequestBody UserRequestDTO userRequestDTO, Authentication authentication) {
        try {
            UserResponseDTO userResponseDTO = userService.updateUser(userRequestDTO, authentication.getName());
            return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable int userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>("User deleted succesfully!", HttpStatus.OK);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
