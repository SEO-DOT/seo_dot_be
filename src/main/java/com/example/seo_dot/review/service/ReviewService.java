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
import com.example.seo_dot.user.domain.enums.Platform;
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
    private final ReviewLikeRepository reviewLikeRepository;

    @Transactional
    public MessageResponseDTO createReview(Long bookId, ReviewCreateRequestDTO reviewCreateRequestDTO, User user) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException());
        Review review = Review.createReview(book, user, reviewCreateRequestDTO);
        reviewRepository.save(review);

        List<Review> findReviews = reviewRepository.findByBookId(bookId);
        book.updateScore(findReviews);
        return MessageResponseDTO.createSuccessMessage201();
    }

    public Slice<ReviewListResponseDTO> getReviews(ReviewPageParam reviewPageParam, Long bookId, User user) {
        User user1 = new User("email","nickname", Platform.GOOGLE);
        bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException());

        PageRequest pageRequest = PageRequest.of(reviewPageParam.getPage(), reviewPageParam.getPer());
        Slice<Review> reviews = null;

        if (reviewPageParam.getSort().equals("new")) {
            reviews = reviewRepository.getReviewsOrderByCreatedAtDesc(bookId, user1.getId(), pageRequest);
        }
        if (reviewPageParam.getSort().equals("hot")) {
            reviews = reviewRepository.getReviewsOrderByLikesDesc(bookId, user1.getId(), pageRequest);
        }

        List<Long> likedReviewIds = reviewLikeRepository.findReviewIdsByUserIdANDBookId(user1.getId(), bookId);
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
    public MessageResponseDTO modifyReview(Long bookId, Long reviewId, ReviewModifyRequestDTO reviewModifyRequestDTO, User user) {
        bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException());

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException());

        if (!review.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException();
        }

        review.updateReview(reviewModifyRequestDTO);
        return MessageResponseDTO.createSuccessMessage200();
    }

    @Transactional
    public MessageResponseDTO deleteReview(Long bookId, Long reviewId, User user) {
        bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException());
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException());

        if (!review.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException();
        }

        review.deleteReview();
        return MessageResponseDTO.createSuccessMessage200();
    }
}
