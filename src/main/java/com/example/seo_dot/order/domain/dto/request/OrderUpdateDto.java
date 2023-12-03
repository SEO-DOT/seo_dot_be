package com.example.seo_dot.order.domain.dto.request;

import lombok.Getter;

@Getter
public class OrderUpdateDto {

    private String receiverMemo;
    private Long reserveMoney;
    private boolean paymentAgreementStatus;
}
