package com.example.seo_dot.review.controller;

import com.example.seo_dot.book.dto.response.PageDto;
import com.example.seo_dot.global.dto.MessageResponseDTO;
import com.example.seo_dot.review.dto.request.ReviewPageParam;
import com.example.seo_dot.review.dto.request.ReviewCreateRequestDTO;
import com.example.seo_dot.review.dto.request.ReviewModifyRequestDTO;
import com.example.seo_dot.review.dto.response.ReviewListResponseDTO;
import com.example.seo_dot.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/api/books/{bookId}/reviews")
    public ResponseEntity<MessageResponseDTO> createReview(@PathVariable Long bookId, @RequestBody ReviewCreateRequestDTO reviewCreateRequestDTO, Long userId) {
        MessageResponseDTO response = reviewService.createReview(bookId, reviewCreateRequestDTO, 1L);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/books/{bookId}/reviews")
    public ResponseEntity<PageDto<List<ReviewListResponseDTO>>> getReviews(@PathVariable Long bookId, @ModelAttribute ReviewPageParam reviewPageParam, Long userId) {
        Slice<ReviewListResponseDTO> result = reviewService.getReviews(reviewPageParam, bookId, 1L);
        PageDto<List<ReviewListResponseDTO>> response = new PageDto<>(result.getContent(), result.hasNext());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/api/books/{bookId}/reviews/{reviewId}")
    public ResponseEntity<MessageResponseDTO> modifyReview(@PathVariable Long bookId, @PathVariable Long reviewId, @RequestBody ReviewModifyRequestDTO reviewModifyRequestDTO, Long userId) {
        MessageResponseDTO response = reviewService.modifyReview(bookId, reviewId, reviewModifyRequestDTO, 1L);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/api/books/{bookId}/reviews/{reviewId}")
    public ResponseEntity<MessageResponseDTO> deleteComment(@PathVariable Long bookId, @PathVariable Long reviewId, Long userId) {
        MessageResponseDTO response = reviewService.deleteReview(bookId, reviewId, 1L);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
