package com.example.seo_dot.payment.service;

import com.example.seo_dot.order.domain.Order;
import com.example.seo_dot.order.domain.OrderRequestDto;
import com.example.seo_dot.order.repository.OrderRepository;
import com.example.seo_dot.payment.domain.Payment;
import com.example.seo_dot.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public void saveOrder(Long userId, OrderRequestDto orderRequestDto) {

        Order order = orderRepository.findByOrderUid(orderRequestDto.getOrderNumber()).orElseThrow();
        if (order.getPrice() != orderRequestDto.getOrderPrice()) {
            throw new IllegalArgumentException("결제금액이 일치하지 않습니다");
        }
        Payment payment = Payment.builder()
                .price(orderRequestDto.getOrderPrice())
                .userId(userId)
                .orderUid(orderRequestDto.getOrderNumber())
                .build();

        paymentRepository.save(payment);
    }
}