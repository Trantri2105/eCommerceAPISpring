package com.trantring.ecommerce.service.implement;

import com.trantring.ecommerce.dao.CartItemRepository;
import com.trantring.ecommerce.entity.Cart;
import com.trantring.ecommerce.entity.CartItem;
import com.trantring.ecommerce.entity.Product;
import com.trantring.ecommerce.service.CartItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {
    private CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Optional<CartItem> findCartItemByCartAndProduct(Cart cart, Product product) {
        return cartItemRepository.findByCartAndProduct(cart, product);
    }

    @Override
    public void delete(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }


}
