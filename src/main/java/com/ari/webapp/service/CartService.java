package com.ari.webapp.service;

import com.ari.webapp.dto.CartDto;
import com.ari.webapp.dto.CartItemCreateDto;
import com.ari.webapp.dto.CartItemDto;
import com.ari.webapp.dto.OrderCreateDto;
import com.ari.webapp.dto.OrderDto;
import com.ari.webapp.model.Cart;
import com.ari.webapp.model.CartItem;
import com.ari.webapp.model.Product;
import com.ari.webapp.model.User;
import com.ari.webapp.repository.CartItemRepository;
import com.ari.webapp.repository.CartRepository;
import com.ari.webapp.repository.ProductRepository;
import com.ari.webapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       UserRepository userRepository,
                       ProductRepository productRepository,
                       OrderService orderService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderService = orderService;
    }

    public CartDto getCartByUserId(Long userId) {
        Cart cart = findOrCreateCart(userId);
        return convertToDto(cart);
    }

    public CartDto addItem(Long userId, CartItemCreateDto request) {
        Cart cart = findOrCreateCart(userId);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst().orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            existingItem.setPrice(product.getPrice());
            cartItemRepository.save(existingItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice());
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
            cartItemRepository.save(cartItem);
        }

        cart.calculateTotalPrice();
        cartRepository.save(cart);

        return convertToDto(cart);
    }

    public CartDto updateItemQuantity(Long userId, Long itemId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }

        Cart cart = findOrCreateCart(userId);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        item.setQuantity(quantity);
        cartItemRepository.save(item);

        cart.calculateTotalPrice();
        cartRepository.save(cart);

        return convertToDto(cart);
    }

    public CartDto removeItem(Long userId, Long itemId) {
        Cart cart = findOrCreateCart(userId);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        cart.getItems().remove(item);
        cartItemRepository.delete(item);

        cart.calculateTotalPrice();
        cartRepository.save(cart);

        return convertToDto(cart);
    }

    public CartDto clearCart(Long userId) {
        Cart cart = findOrCreateCart(userId);
        cart.getItems().forEach(cartItemRepository::delete);
        cart.getItems().clear();
        cart.setTotalPrice(null);
        cartRepository.save(cart);

        return convertToDto(cart);
    }

    public OrderDto checkout(Long userId) {
        Cart cart = findOrCreateCart(userId);
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        OrderCreateDto orderCreateDto = new OrderCreateDto();
        orderCreateDto.setUserId(userId);
        orderCreateDto.setItems(cart.getItems().stream().map(item -> {
            com.ari.webapp.dto.OrderItemCreateDto orderItemCreateDto = new com.ari.webapp.dto.OrderItemCreateDto();
            orderItemCreateDto.setProductId(item.getProduct().getId());
            orderItemCreateDto.setQuantity(item.getQuantity());
            return orderItemCreateDto;
        }).collect(Collectors.toList()));

        OrderDto order = orderService.createOrder(orderCreateDto);

        clearCart(userId);

        return order;
    }

    private Cart findOrCreateCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setTotalPrice(null);
            return cartRepository.save(cart);
        });
    }

    private CartDto convertToDto(Cart cart) {
        CartDto dto = new CartDto();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser().getId());
        dto.setTotalPrice(cart.getTotalPrice());

        List<CartItemDto> itemDtos = cart.getItems().stream().map(item -> {
            CartItemDto ci = new CartItemDto();
            ci.setId(item.getId());
            ci.setProductId(item.getProduct().getId());
            ci.setProductName(item.getProduct().getName());
            ci.setQuantity(item.getQuantity());
            ci.setPrice(item.getPrice());
            return ci;
        }).collect(Collectors.toList());

        dto.setItems(itemDtos);
        return dto;
    }
}