package com.example.seo_dot.comment.review.repository;

import com.example.seo_dot.comment.review.dto.response.BestReviewListResponseDTO;
import com.example.seo_dot.comment.review.domain.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface QReviewRepository {

    Slice<Review> getReviewsOrderByCreatedAtDesc(Long bookId, Long userId, Pageable pageable);

    Slice<Review> getReviewsOrderByLikesDesc(Long bookId, Long userId, Pageable pageable);

    List<BestReviewListResponseDTO> getBestReviews();
}
