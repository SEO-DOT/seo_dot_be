package com.example.seo_dot.review.repository;

import com.example.seo_dot.review.domain.Review;
import com.example.seo_dot.review.dto.response.BestReviewListResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface QReviewRepository {

    Slice<Review> getReviewsOrderByCreatedAtDesc(Long bookId, Long userId, Pageable pageable);

    Slice<Review> getReviewsOrderByLikesDesc(Long bookId, Long userId, Pageable pageable);

    List<BestReviewListResponseDTO> getBestReviews();
}
