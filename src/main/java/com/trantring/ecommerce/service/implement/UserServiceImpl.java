package com.trantring.ecommerce.service.implement;

import com.trantring.ecommerce.dao.UserRepository;
import com.trantring.ecommerce.dto.request.LoginDTO;
import com.trantring.ecommerce.dto.request.RegisterDTO;
import com.trantring.ecommerce.dto.request.RequestParamsDTO;
import com.trantring.ecommerce.dto.request.UserRequestDTO;
import com.trantring.ecommerce.entity.Cart;
import com.trantring.ecommerce.entity.User;
import com.trantring.ecommerce.security.JWTService;
import com.trantring.ecommerce.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private JWTService jwtService;

    private AuthenticationManager authenticationManager;


    public UserServiceImpl(UserRepository userRepository, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void registerUser(RegisterDTO registerDTO) {
        try {
            User user = new User();
            user.setEmail(registerDTO.getEmail());
            user.setPassword((new BCryptPasswordEncoder()).encode(registerDTO.getPassword()));
            user.setFirstName(registerDTO.getFirstname());
            user.setLastName(registerDTO.getLastname());
            user.setRole("ROLE_user");
            Cart cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
            save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException("User already exist with email: " + registerDTO.getEmail());
        }

    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public User findUserById(int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public Page<User> getAllUser(RequestParamsDTO requestParamsDTO) {
        if (requestParamsDTO.getPageSize() > 20) requestParamsDTO.setPageSize(20);
        if (!(new ArrayList<>(List.of("id", "email", "lastName", "firstName")).contains(requestParamsDTO.getSortBy()))) {
            throw new RuntimeException("sortBy is invalid!");
        }
        if (!(new ArrayList<>(List.of("asc", "desc")).contains(requestParamsDTO.getSortOrder()))) {
            throw new RuntimeException("sortOrder is invalid!");
        }
        Sort sort = requestParamsDTO.getSortOrder().equals("asc") ? Sort.by(requestParamsDTO.getSortBy()) : Sort.by(requestParamsDTO.getSortBy()).descending();
        return userRepository.findAll(PageRequest.of(requestParamsDTO.getPageNumber(), requestParamsDTO.getPageSize(), sort));
    }

    @Override
    public User updateUser(UserRequestDTO userRequestDTO, String email) {
        User user = findUserByEmail(email);
        if (userRequestDTO.getPassword() != null) {
            user.setPassword((new BCryptPasswordEncoder()).encode(userRequestDTO.getPassword()));
        }
        if (userRequestDTO.getFirstName() != null) {
            user.setFirstName(userRequestDTO.getFirstName());
        }
        if (userRequestDTO.getLastName() != null) {
            user.setLastName(userRequestDTO.getLastName());
        }
        return save(user);
    }

    @Override
    public void deleteUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
        userRepository.delete(user);
    }

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()));
        return jwtService.generateToken(findUserByEmail(loginDTO.getEmail()).getId(), (long) (1000 * 60 * 15));
    }

}
