package com.trantring.ecommerce.controller;

import com.trantring.ecommerce.dto.request.LoginDTO;
import com.trantring.ecommerce.dto.request.RegisterDTO;
import com.trantring.ecommerce.dto.response.AuthResponseDTO;
import com.trantring.ecommerce.entity.User;
import com.trantring.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin
public class AuthenticationController {
    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        String accessToken = userService.login(loginDTO);
        User user = userService.findUserByEmail(loginDTO.getEmail());
        return new ResponseEntity<>(new AuthResponseDTO(accessToken, "Bearer", user.getRole()), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO registerDTO) {
        userService.registerUser(registerDTO);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        return new ResponseEntity<>("Wrong email or password!", HttpStatus.BAD_REQUEST);
    }
}
