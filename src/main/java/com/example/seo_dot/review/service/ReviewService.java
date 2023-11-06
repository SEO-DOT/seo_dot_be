package com.example.seo_dot.review.service;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.book.repository.BookRepository;
import com.example.seo_dot.global.dto.MessageResponseDTO;
import com.example.seo_dot.review.dto.request.ReviewPageParam;
import com.example.seo_dot.review.dto.request.ReviewCreateRequestDTO;
import com.example.seo_dot.review.domain.Review;
import com.example.seo_dot.review.dto.request.ReviewModifyRequestDTO;
import com.example.seo_dot.review.dto.response.ReviewListResponseDTO;
import com.example.seo_dot.review.repository.ReviewLikeRepository;
import com.example.seo_dot.review.repository.ReviewRepository;
import com.example.seo_dot.user.domain.User;
import com.example.seo_dot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    @Transactional
    public MessageResponseDTO createReview(Long bookId, ReviewCreateRequestDTO reviewCreateRequestDTO, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException());
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());
        Review review = Review.createReview(bookId, user, reviewCreateRequestDTO);
        reviewRepository.save(review);

        List<Review> findReviews = reviewRepository.findByBookId(bookId);
        book.updateScore(findReviews);
        return MessageResponseDTO.createSuccessMessage201();
    }

    public Slice<ReviewListResponseDTO> getReviews(ReviewPageParam reviewPageParam, Long bookId, Long userId) {
        bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException());

        PageRequest pageRequest = PageRequest.of(reviewPageParam.getPage(), reviewPageParam.getPer());
        Slice<Review> reviews = null;

        if (reviewPageParam.getSort().equals("new")) {
            reviews = reviewRepository.findReviewsByBookIdAndDeletedFalseOrderByCreatedAtDesc(bookId, pageRequest);
        }
        if (reviewPageParam.getSort().equals("hot")) {
            reviews = reviewRepository.findReviewsByBookIdAndDeletedFalseOrderByLikesDesc(bookId, pageRequest);
        }

        List<Long> likedReviewIds = reviewLikeRepository.findReviewIdsByUserIdANDBookId(userId, bookId);
        List<ReviewListResponseDTO> reviewListResponseDTOList = reviews.stream()
                .map(review -> {
                    boolean liked = likedReviewIds.contains(review.getId());
                    ReviewListResponseDTO reviewDTO = new ReviewListResponseDTO(review);
                    reviewDTO.setLiked(liked);
                    return reviewDTO;
                })
                .toList();
        SliceImpl<ReviewListResponseDTO> response = new SliceImpl<>(reviewListResponseDTOList, reviews.getPageable(), reviews.hasNext());
        return response;
    }

    @Transactional
    public MessageResponseDTO modifyReview(Long bookId, Long reviewId, ReviewModifyRequestDTO reviewModifyRequestDTO, Long userId) {
        bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException());

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException());

        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException();
        }

        review.updateReview(reviewModifyRequestDTO);
        return MessageResponseDTO.createSuccessMessage200();
    }

    @Transactional
    public MessageResponseDTO deleteReview(Long bookId, Long reviewId, Long userId) {
        bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException());
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException());

        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException();
        }

        review.deleteReview();
        return MessageResponseDTO.createSuccessMessage200();
    }
}
