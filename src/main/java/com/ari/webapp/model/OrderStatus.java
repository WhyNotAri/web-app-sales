package com.ari.webapp.model;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING,
    PAID,
    SHIPPED,
    CANCELED
}
