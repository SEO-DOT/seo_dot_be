package com.example.seo_dot.comment.review.repository;

import com.example.seo_dot.comment.review.domain.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewLikeRepository  extends JpaRepository<ReviewLike, Long> {
    @Query("SELECT rl.review.id FROM ReviewLike rl WHERE rl.user.id = :userId And rl.review.book.id = :bookId")
    List<Long> findReviewIdsByUserIdANDBookId(Long userId, Long bookId);

    ReviewLike findReviewLikeByReviewIdAndUserId(Long id, Long userId);
    @Query("SELECT rl.review.id FROM ReviewLike rl WHERE rl.user.id = :userId")
    List<Long> findReviewIdsByUserId(Long userId);
}
