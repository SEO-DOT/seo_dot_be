package com.example.seo_dot.comment.review.repository;

import com.example.seo_dot.comment.review.domain.Review;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, QReviewRepository {

    List<Review> findByBookId(Long bookId);

    Slice<Review> findReviewsByBookIdAndDeletedFalseOrderByCreatedAtDesc(Long bookId, PageRequest pageRequest);

    Slice<Review> findReviewsByBookIdAndDeletedFalseOrderByLikesDesc(Long bookId, PageRequest pageRequest);

    @Query("SELECT r FROM Review r join fetch r.book b order by r.createdAt desc")
    Slice<Review> findByUserIdOrderByCreatedAtDesc(PageRequest pageRequest, Long userId);

    Integer countByUserId(Long userId);
}
