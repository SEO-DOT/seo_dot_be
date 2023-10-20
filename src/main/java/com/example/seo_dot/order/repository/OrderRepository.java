package com.example.seo_dot.order.repository;

import com.example.seo_dot.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
