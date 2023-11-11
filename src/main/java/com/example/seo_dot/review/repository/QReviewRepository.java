package com.example.seo_dot.review.repository;

import com.example.seo_dot.review.domain.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface QReviewRepository {

    Slice<Review> getReviewsOrderByCreatedAtDesc(Long bookId, Long userId, Pageable pageable);

    Slice<Review> getReviewsOrderByLikesDesc(Long bookId, Long userId, Pageable pageable);

}
