package com.example.seo_dot.payment.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long price;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;
    private String paymentUid;

    public void changePaymentStatus(PaymentStatus paymentStatus, String paymentUid) {
        this.paymentStatus = paymentStatus;
        this.paymentUid =paymentUid;
    }

}
