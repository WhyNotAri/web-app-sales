package com.ari.webapp.controller;

import com.ari.webapp.dto.CartDto;
import com.ari.webapp.dto.CartItemCreateDto;
import com.ari.webapp.dto.CartItemUpdateDto;
import com.ari.webapp.dto.OrderDto;
import com.ari.webapp.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PreAuthorize("hasRole('USER') and #userId == authentication.principal.id")
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long userId) {
        CartDto cartDto = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cartDto);
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<CartDto> addItem(@PathVariable Long userId, @Valid @RequestBody CartItemCreateDto request) {
        CartDto cartDto = cartService.addItem(userId, request);
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/{userId}/items/{itemId}")
    public ResponseEntity<CartDto> updateItem(@PathVariable Long userId, @PathVariable Long itemId, @Valid @RequestBody CartItemUpdateDto request) {
        CartDto cartDto = cartService.updateItemQuantity(userId, itemId, request.getQuantity());
        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<CartDto> removeItem(@PathVariable Long userId, @PathVariable Long itemId) {
        CartDto cartDto = cartService.removeItem(userId, itemId);
        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<CartDto> clearCart(@PathVariable Long userId) {
        CartDto cartDto = cartService.clearCart(userId);
        return ResponseEntity.ok(cartDto);
    }

    @PreAuthorize("hasRole('USER') and #userId == authentication.principal.id")
    @PostMapping("/{userId}/checkout")
    public ResponseEntity<OrderDto> checkout(@PathVariable Long userId) {
        OrderDto orderDto = cartService.checkout(userId);
        return ResponseEntity.ok(orderDto);
    }
}
