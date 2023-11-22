package com.example.seo_dot.payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentCallbackRequest {
    private String paymentUid;
    private String orderUid;
}
