package com.example.seo_dot.order.repository;

import com.example.seo_dot.order.domain.Order;
import com.example.seo_dot.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Slice<Order> findSliceByUser(User user, Pageable pageable);

    Optional<Order> findByUserAndOrderNum(User user, String orderNum);
}
