package com.trantring.ecommerce.Service.implement;

import com.trantring.ecommerce.DAO.CartRepository;
import com.trantring.ecommerce.DTO.CartItemDTO;
import com.trantring.ecommerce.DTO.Response.CartResponseDTO;
import com.trantring.ecommerce.Entity.Cart;
import com.trantring.ecommerce.Entity.CartItem;
import com.trantring.ecommerce.Entity.Product;
import com.trantring.ecommerce.Entity.Users;
import com.trantring.ecommerce.Exception.APIException;
import com.trantring.ecommerce.Service.CartItemService;
import com.trantring.ecommerce.Service.CartService;
import com.trantring.ecommerce.Service.ProductService;
import com.trantring.ecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;

    private UserService userService;

    private ProductService productService;

    private CartItemService cartItemService;

    @Autowired
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
        Users user = userService.findUserByEmail(email);
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new APIException("Cart not found!"));
        Product product = productService.findProductById(productId);
        if (cartItemService.findCartItemByCartAndProduct(cart, product).isPresent())
            throw new APIException("Product already add to cart!");
        cart.getCartItems().add(new CartItem(cart, product, quantity, product.getPrice() * quantity));
        cart.setTotalPrice(cart.getTotalPrice() + quantity * product.getPrice());
        save(cart);
    }

    @Override
    public CartResponseDTO getCartInfo(String email) {
        Users user = userService.findUserByEmail(email);
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new APIException("Cart not found!"));
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();
        cart.getCartItems().forEach(cartItem -> {
            cartItemDTOList.add(new CartItemDTO(cartItem.getProduct().getId(), cartItem.getQuantity(), cartItem.getPrice()));
        });
        return new CartResponseDTO(cart.getTotalPrice(), cartItemDTOList);
    }

    @Override
    public CartResponseDTO updateCart(String email, CartItemDTO cartItemDTO) {
        Users user = userService.findUserByEmail(email);
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new APIException("Cart not found!"));
        Product product = productService.findProductById(cartItemDTO.getProductId());
        List<CartItem> cartItems = cart.getCartItems();
        CartItem cartItem = findCartItemsByProductId(cartItems, cartItemDTO.getProductId());
        if (cartItem == null) throw new APIException("Product is not added to cart!");
        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getPrice() + product.getPrice() * cartItemDTO.getQuantity());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setPrice(product.getPrice() * cartItemDTO.getQuantity());
        Cart updatedCart = cartRepository.save(cart);
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();
        updatedCart.getCartItems().forEach(item -> {
            cartItemDTOList.add(new CartItemDTO(item.getProduct().getId(), item.getQuantity(), item.getPrice()));
        });
        return new CartResponseDTO(updatedCart.getTotalPrice(), cartItemDTOList);
    }

    @Override
    public void deleteProductFromCart(String email, int productId) {
        Users user = userService.findUserByEmail(email);
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new APIException("Cart not found!"));
        Product product = productService.findProductById(productId);
        List<CartItem> cartItems = cart.getCartItems();
        CartItem cartItem = findCartItemsByProductId(cartItems, productId);
        if (cartItem == null) throw new APIException("Product is not added to cart!");
        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getPrice());
        cart.getCartItems().remove(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public Cart findCartByUser(Users user) {
        return cartRepository.findByUser(user).orElseThrow(() -> new APIException("Cart not found!"));
    }

    private CartItem findCartItemsByProductId(List<CartItem> cartItems, int productId) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getId() == productId) return cartItem;
        }
        return null;
    }
}
