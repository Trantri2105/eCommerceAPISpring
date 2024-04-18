package com.trantring.ecommerce.Controller;

import com.trantring.ecommerce.DTO.Response.AuthResponseDTO;
import com.trantring.ecommerce.DTO.Request.LoginDTO;
import com.trantring.ecommerce.DTO.Request.RegisterDTO;
import com.trantring.ecommerce.Exception.APIException;
import com.trantring.ecommerce.Security.JWTService;
import com.trantring.ecommerce.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JWTService jwtService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()));
        String accessToken = jwtService.generateToken(userService.findUserByEmail(loginDTO.getEmail()).getId(), (long) (1000 * 60 * 15));
        return new ResponseEntity<>(new AuthResponseDTO(accessToken, "Bearer"), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO registerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.registerUser(registerDTO);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        return new ResponseEntity<>("Wrong email or password!", HttpStatus.BAD_REQUEST);
    }
}
