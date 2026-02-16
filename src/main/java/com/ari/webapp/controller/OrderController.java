package com.ari.webapp.controller;

import com.ari.webapp.dto.CreateOrderRequest;
import com.ari.webapp.model.Order;
import com.ari.webapp.model.OrderStatus;
import com.ari.webapp.model.User;
import com.ari.webapp.service.OrderService;
import com.ari.webapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public List<Order> findAllOrders() {
        return orderService.findAllOrders();
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody CreateOrderRequest request) {
        User user = userService.findById(request.getUserId());
        return orderService.createOrder(user, request.getItems());
    }

    @PutMapping("/{id}")
    public Order updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatus newStatus) {
        return orderService.updateOrderStatus(id, newStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}
