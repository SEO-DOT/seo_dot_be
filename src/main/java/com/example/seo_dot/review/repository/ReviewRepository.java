package com.example.seo_dot.review.repository;

import com.example.seo_dot.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
