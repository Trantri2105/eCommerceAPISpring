package com.trantring.ecommerce.Service.implement;

import com.trantring.ecommerce.DAO.UserRepository;
import com.trantring.ecommerce.DTO.Request.RegisterDTO;
import com.trantring.ecommerce.DTO.Response.UserPageResponseDTO;
import com.trantring.ecommerce.DTO.Request.UserRequestDTO;
import com.trantring.ecommerce.DTO.Response.UserResponseDTO;
import com.trantring.ecommerce.Entity.Cart;
import com.trantring.ecommerce.Entity.Users;
import com.trantring.ecommerce.Exception.APIException;
import com.trantring.ecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Users save(Users user) {
        return userRepository.save(user);
    }

    @Override
    public void registerUser(RegisterDTO registerDTO) {
        try {
            Users user = new Users();
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
            throw new APIException("User already exist with email: " + registerDTO.getEmail());
        }

    }

    @Override
    public Users findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new APIException("User not found!"));
    }

    @Override
    public Users findUserById(int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new APIException("User not found!"));
    }

    @Override
    public UserPageResponseDTO getAllUser(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        if (!(new ArrayList<>(List.of("id", "email", "lastName", "firstName")).contains(sortBy))) {
            throw new APIException("sortBy is invalid!");
        }
        if (!(new ArrayList<>(List.of("asc", "desc")).contains(sortOrder))) {
            throw new APIException("sortOrder is invalid!");
        }
        Sort sort = sortOrder.equals("asc") ? Sort.by(sortBy) : Sort.by(sortBy).descending();
        Page<Users> users = userRepository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        return new UserPageResponseDTO(users);
    }

    @Override
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO, String email) {
        Users user = findUserByEmail(email);
        if (userRequestDTO.getPassword() != null) {
            user.setPassword((new BCryptPasswordEncoder()).encode(userRequestDTO.getPassword()));
        }
        if (userRequestDTO.getFirstName() != null) {
            user.setFirstName(userRequestDTO.getFirstName());
        }
        if (userRequestDTO.getLastName() != null) {
            user.setLastName(userRequestDTO.getLastName());
        }
        Users updatedUser = save(user);
        return new UserResponseDTO(updatedUser.getId(), updatedUser.getEmail(), updatedUser.getFirstName(), updatedUser.getLastName());
    }

    @Override
    public void deleteUser(int userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new APIException("User not found!"));
        userRepository.delete(user);
    }

}
