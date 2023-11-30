package com.example.seo_dot.cart.repository;

import com.example.seo_dot.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>, QCartRepository {

    Integer countByUserId(Long userId);

    Cart findByBookId(Long bookId);

    Cart findByIdAndUserId(Long id, Long userId);
}
