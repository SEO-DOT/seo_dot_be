package com.example.seo_dot.payment.repository;

import com.example.seo_dot.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
