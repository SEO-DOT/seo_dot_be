package com.example.seo_dot.order.repository;

import com.example.seo_dot.order.domain.OrderBookInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBookInfoRepository extends JpaRepository<OrderBookInfo, Long> {
}
