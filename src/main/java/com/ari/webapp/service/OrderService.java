package com.ari.webapp.service;

import com.ari.webapp.dto.OrderCreateDto;
import com.ari.webapp.dto.OrderDto;
import com.ari.webapp.dto.OrderItemDto;
import com.ari.webapp.model.*;
import com.ari.webapp.repository.OrderRepository;
import com.ari.webapp.repository.ProductRepository;
import com.ari.webapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public OrderDto createOrder(OrderCreateDto request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(EntityNotFoundException::new);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> items = request.getItems().stream()
                .map(itemDto -> {
                    Product product = productRepository.findById(itemDto.getProductId()).orElseThrow(EntityNotFoundException::new);

                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(itemDto.getQuantity());
                    orderItem.setPrice(product.getPrice());
                    orderItem.setOrder(order);
                    return orderItem;
                }).collect(Collectors.toList());
        order.setItems(items);

        Double total = items.stream().mapToDouble(OrderItem::getQuantity).sum();
        order.setTotalPrice(total);

        Order savedOrder = orderRepository.save(order);

        List<OrderItemDto> itemDto = savedOrder.getItems().stream()
                .map(item -> {
                    OrderItemDto orderItemDto = new OrderItemDto();
                    orderItemDto.setProductName(item.getProduct().getName());
                    orderItemDto.setQuantity(item.getQuantity());
                    orderItemDto.setPrice(item.getPrice());
                    return orderItemDto;
                }).collect(Collectors.toList());

        OrderDto orderDto = new OrderDto();
        orderDto.setId(savedOrder.getId());
        orderDto.setUserName(savedOrder.getUser().getEmail());
        orderDto.setOrderItemDtoList(itemDto);
        orderDto.setTotalPrice(savedOrder.getTotalPrice());
        orderDto.setOrderStatus(savedOrder.getStatus());
        orderDto.setOrderDate(savedOrder.getOrderDate());

        return orderDto;
    }

    public OrderDto findOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("order not found"));

        List<OrderItemDto> orderItemDtoList = order.getItems().stream()
                .map(item -> {
                    OrderItemDto orderItemDto = new OrderItemDto();
                    orderItemDto.setProductName(item.getProduct().getName());
                    orderItemDto.setQuantity(item.getQuantity());
                    orderItemDto.setPrice(item.getPrice());
                    return orderItemDto;
                }).collect(Collectors.toList());

        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserName(order.getUser().getEmail());
        orderDto.setOrderItemDtoList(orderItemDtoList);
        orderDto.setOrderStatus(order.getStatus());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setOrderDate(order.getOrderDate());

        return orderDto;
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
