package com.example.seo_dot.order.domain;

import com.example.seo_dot.payment.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long price;
    private String orderUid;
    private Long userId;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;

    public void changePaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
