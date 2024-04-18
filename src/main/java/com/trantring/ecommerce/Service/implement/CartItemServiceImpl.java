package com.trantring.ecommerce.Service.implement;

import com.trantring.ecommerce.DAO.CartItemRepository;
import com.trantring.ecommerce.Entity.Cart;
import com.trantring.ecommerce.Entity.CartItem;
import com.trantring.ecommerce.Entity.Product;
import com.trantring.ecommerce.Service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {
    private CartItemRepository cartItemRepository;

    @Autowired
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
