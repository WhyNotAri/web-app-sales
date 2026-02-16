package com.ari.webapp.service;

import com.ari.webapp.dto.OrderItemDto;
import com.ari.webapp.model.*;
import com.ari.webapp.repository.OrderRepository;
import com.ari.webapp.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order createOrder(User user, List<OrderItemDto> itemsDto) {
        Order order = new Order();
        order.setUser(user);

        List<OrderItems> orderItems = new ArrayList<>();
        for (OrderItemDto itemDto : itemsDto) {
            Product product = productRepository.findById(itemDto.getProductId()).orElseThrow(() -> new EntityNotFoundException("product not found"));
            if (product.getStock() < 0 || product.getStock() < itemDto.getQuantity()) {
                throw new RuntimeException("not enough stock");
            }

            product.setStock(product.getStock() - itemDto.getQuantity());
            productRepository.save(product);

            OrderItems orderItem = new OrderItems();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }
        order.setItems(orderItems);
        order.setOrderDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("order not found"));
    }
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order updateStatus = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("order not found"));
        switch (updateStatus.getStatus()) {
            case PENDING:
                if(newStatus == OrderStatus.PAID || newStatus == OrderStatus.CANCELED) {
                    updateStatus.setStatus(newStatus);
                }else {
                    throw new RuntimeException("Invalid order status");
                }
                break;
            case PAID:
                if(newStatus == OrderStatus.SHIPPED || newStatus == OrderStatus.CANCELED) {
                    updateStatus.setStatus(newStatus);
                }else {
                    throw new RuntimeException("Invalid order status");
                }
                break;
            case SHIPPED:
                throw new RuntimeException("Invalid order status");

            case CANCELED:
                throw new RuntimeException("The order has been cancelled");
        }
        return orderRepository.save(updateStatus);
    }

    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("order not found"));
        orderRepository.delete(order);
    }
}
