package com.example.seo_dot.review.repository;

import com.example.seo_dot.review.domain.Review;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, QReviewRepository {

    List<Review> findByBookId(Long bookId);

    Slice<Review> findReviewsByBookIdAndDeletedFalseOrderByCreatedAtDesc(Long bookId, PageRequest pageRequest);

    Slice<Review> findReviewsByBookIdAndDeletedFalseOrderByLikesDesc(Long bookId, PageRequest pageRequest);
}
