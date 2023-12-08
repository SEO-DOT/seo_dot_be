package com.example.seo_dot.comment.review.service;

import com.example.seo_dot.book.repository.BookRepository;
import com.example.seo_dot.comment.review.domain.Review;
import com.example.seo_dot.global.dto.MessageResponseDTO;
import com.example.seo_dot.comment.review.domain.ReviewLike;
import com.example.seo_dot.comment.review.repository.ReviewLikeRepository;
import com.example.seo_dot.comment.review.repository.ReviewRepository;
import com.example.seo_dot.user.domain.User;
import com.example.seo_dot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewLikeService {

    private final ReviewLikeRepository reviewLikeRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public MessageResponseDTO reviewLike(Long bookId, Long reviewId, Long userId) {
        bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException());
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException());
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());
        ReviewLike like = reviewLikeRepository.findReviewLikeByReviewIdAndUserId(review.getId(), userId);

        if (like != null) {
            review.decreaseLikeCount();
            reviewLikeRepository.delete(like);

        } else {
            review.increaseLikeCount();
            reviewLikeRepository.save(new ReviewLike(user, review));
        }
        return MessageResponseDTO.createSuccessMessage200();
    }
}
