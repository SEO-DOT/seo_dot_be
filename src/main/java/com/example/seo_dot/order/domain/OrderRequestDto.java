package com.example.seo_dot.order.domain;

import lombok.Getter;

@Getter
public class OrderRequestDto {
    private Long userId;
    private Long bookId;
    private long orderPrice;
    private int orderCount;
    private int orderDiscount;
    private String phoneNumber;
    private String orderNumber;
    private String address;
    private String paymentMethod;
}
