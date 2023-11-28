package com.example.seo_dot.payment.domain;

import com.example.seo_dot.payment.domain.enums.PaymentStatus;
import com.example.seo_dot.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderUid;
    private Long userId;
    private Long price;
}