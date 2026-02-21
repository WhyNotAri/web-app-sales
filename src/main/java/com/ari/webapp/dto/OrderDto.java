package com.ari.webapp.dto;

import com.ari.webapp.model.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String userName;
    private List<OrderItemDto> orderItemDtoList;
    private Double totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;

    public OrderDto(Long id, String userName, List<OrderItemDto> orderItemDtoList, Double totalPrice, OrderStatus orderStatus, LocalDateTime orderDate) {
        this.id = id;
        this.userName = userName;
        this.orderItemDtoList = orderItemDtoList;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }
}
