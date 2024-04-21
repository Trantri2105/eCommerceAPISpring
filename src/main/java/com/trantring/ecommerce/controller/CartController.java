package com.trantring.ecommerce.controller;

import com.trantring.ecommerce.dto.CartItemDTO;
import com.trantring.ecommerce.dto.response.CartResponseDTO;
import com.trantring.ecommerce.entity.Cart;
import com.trantring.ecommerce.mapper.CartMapper;
import com.trantring.ecommerce.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    private CartService cartService;
    private CartMapper cartMapper;

    public CartController(CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @PostMapping("/cart")
    public ResponseEntity<String> addProductToCart(@RequestBody @Valid CartItemDTO cartItemDTO, Authentication authentication) {
        cartService.addProductToCart(authentication.getName(), cartItemDTO.getProductId(), cartItemDTO.getQuantity());
        return new ResponseEntity<>("Product added to cart successfully!", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @GetMapping("/cart")
    public ResponseEntity<CartResponseDTO> getCartInfo(Authentication authentication) {
        Cart cart = cartService.getCartInfo(authentication.getName());
        return new ResponseEntity<>(cartMapper.cartToCartResponseDTO(cart), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @PatchMapping("/cart")
    public ResponseEntity<CartResponseDTO> updateCart(Authentication authentication, @RequestBody @Valid CartItemDTO cartItemDTO) {
        Cart cart = cartService.updateCart(authentication.getName(), cartItemDTO);
        return new ResponseEntity<>(cartMapper.cartToCartResponseDTO(cart), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @DeleteMapping("/cart/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable int productId, Authentication authentication) {
        cartService.deleteProductFromCart(authentication.getName(), productId);
        return new ResponseEntity<>("Product deleted from cart successfully!", HttpStatus.OK);
    }
}
