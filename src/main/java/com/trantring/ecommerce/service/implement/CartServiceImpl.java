package com.trantring.ecommerce.service.implement;

import com.trantring.ecommerce.dao.CartRepository;
import com.trantring.ecommerce.dto.CartItemDTO;
import com.trantring.ecommerce.entity.Cart;
import com.trantring.ecommerce.entity.CartItem;
import com.trantring.ecommerce.entity.Product;
import com.trantring.ecommerce.entity.User;
import com.trantring.ecommerce.service.CartItemService;
import com.trantring.ecommerce.service.CartService;
import com.trantring.ecommerce.service.ProductService;
import com.trantring.ecommerce.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;

    private UserService userService;

    private ProductService productService;

    private CartItemService cartItemService;

    public CartServiceImpl(CartRepository cartRepository, UserService userService, ProductService productService, CartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productService = productService;
        this.cartItemService = cartItemService;
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void addProductToCart(String email, int productId, int quantity) {
        User user = userService.findUserByEmail(email);
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found!"));
        Product product = productService.findProductById(productId);
        if (cartItemService.findCartItemByCartAndProduct(cart, product).isPresent())
            throw new RuntimeException("Product already add to cart!");
        cart.getCartItems().add(new CartItem(cart, product, quantity, product.getPrice() * quantity));
        cart.setTotalPrice(cart.getTotalPrice() + quantity * product.getPrice());
        save(cart);
    }

    @Override
    public Cart getCartInfo(String email) {
        User user = userService.findUserByEmail(email);
        return cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found!"));
    }

    @Override
    public Cart updateCart(String email, CartItemDTO cartItemDTO) {
        User user = userService.findUserByEmail(email);
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found!"));
        Product product = productService.findProductById(cartItemDTO.getProductId());
        List<CartItem> cartItems = cart.getCartItems();
        CartItem cartItem = findCartItemsByProductId(cartItems, cartItemDTO.getProductId());
        if (cartItem == null) throw new RuntimeException("Product is not added to cart!");
        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getPrice() + product.getPrice() * cartItemDTO.getQuantity());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setPrice(product.getPrice() * cartItemDTO.getQuantity());
        return cartRepository.save(cart);
    }

    @Override
    public void deleteProductFromCart(String email, int productId) {
        User user = userService.findUserByEmail(email);
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found!"));
        Product product = productService.findProductById(productId);
        List<CartItem> cartItems = cart.getCartItems();
        CartItem cartItem = findCartItemsByProductId(cartItems, productId);
        if (cartItem == null) throw new RuntimeException("Product is not added to cart!");
        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getPrice());
        cart.getCartItems().remove(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public Cart findCartByUser(User user) {
        return cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found!"));
    }

    private CartItem findCartItemsByProductId(List<CartItem> cartItems, int productId) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getId() == productId) return cartItem;
        }
        return null;
    }
}
