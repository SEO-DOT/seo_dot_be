package com.example.seo_dot.payment.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PayRequestDto {

    private Long userId;
    private Long bookId;
    private int orderPrice;
    private int orderCount;
    private int orderDiscount;
    private String phoneNumber;
    private String orderNumber;
    private String address;
    private String paymentMethod;
}
