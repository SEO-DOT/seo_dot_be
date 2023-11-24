package com.example.seo_dot.payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PayRequestDto {

    private Long userId;
    private Long orderId;
    private String merchantId;
    private int orderPrice;
}
