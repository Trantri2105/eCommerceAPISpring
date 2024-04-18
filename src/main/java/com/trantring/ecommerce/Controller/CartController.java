package com.trantring.ecommerce.Controller;

import com.trantring.ecommerce.DTO.CartItemDTO;
import com.trantring.ecommerce.DTO.Response.CartResponseDTO;
import com.trantring.ecommerce.Exception.APIException;
import com.trantring.ecommerce.Service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @PostMapping("/cart")
    public ResponseEntity<String> addProductToCart(@RequestBody @Valid CartItemDTO cartItemDTO, Authentication authentication, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        try {
            cartService.addProductToCart(authentication.getName(), cartItemDTO.getProductId(), cartItemDTO.getQuantity());
            return new ResponseEntity<>("Product added to cart successfully!", HttpStatus.OK);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @GetMapping("/cart")
    public ResponseEntity<CartResponseDTO> getCartInfo(Authentication authentication) {
        CartResponseDTO cartResponseDTO = cartService.getCartInfo(authentication.getName());
        return new ResponseEntity<>(cartResponseDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @PatchMapping("/cart")
    public ResponseEntity<?> updateCart(Authentication authentication, @RequestBody @Valid CartItemDTO cartItemDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        try {
            CartResponseDTO cartResponseDTO = cartService.updateCart(authentication.getName(), cartItemDTO);
            return new ResponseEntity<>(cartResponseDTO, HttpStatus.OK);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @DeleteMapping("/cart/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable int productId, Authentication authentication) {
        try {
            cartService.deleteProductFromCart(authentication.getName(), productId);
            return new ResponseEntity<>("Product deleted from cart successfully!", HttpStatus.OK);
        } catch (APIException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
