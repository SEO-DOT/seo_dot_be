package com.example.seo_dot.review.controller;

import com.example.seo_dot.book.dto.response.PageDto;
import com.example.seo_dot.global.dto.MessageResponseDTO;
import com.example.seo_dot.global.security.UserDetailsImpl;
import com.example.seo_dot.review.dto.request.ReviewPageParam;
import com.example.seo_dot.review.dto.request.ReviewCreateRequestDTO;
import com.example.seo_dot.review.dto.request.ReviewModifyRequestDTO;
import com.example.seo_dot.review.dto.response.ReviewListResponseDTO;
import com.example.seo_dot.review.service.ReviewService;
import com.example.seo_dot.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/api/books/{bookId}/reviews")
    public ResponseEntity<MessageResponseDTO> createReview(@PathVariable Long bookId, @RequestBody ReviewCreateRequestDTO reviewCreateRequestDTO,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MessageResponseDTO response = reviewService.createReview(bookId, reviewCreateRequestDTO, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/books/{bookId}/reviews")
    public ResponseEntity<PageDto<List<ReviewListResponseDTO>>> getReviews(@PathVariable Long bookId,
                                                                           @ModelAttribute ReviewPageParam reviewPageParam,
                                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Slice<ReviewListResponseDTO> result = reviewService.getReviews(reviewPageParam, bookId, userDetails.getUser());
        PageDto<List<ReviewListResponseDTO>> response = new PageDto<>(result.getContent(), result.hasNext());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/api/books/{bookId}/reviews/{reviewId}")
    public ResponseEntity<MessageResponseDTO> modifyReview(@PathVariable Long bookId, @PathVariable Long reviewId,
                                                           @RequestBody ReviewModifyRequestDTO reviewModifyRequestDTO,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MessageResponseDTO response = reviewService.modifyReview(bookId, reviewId, reviewModifyRequestDTO, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/api/books/{bookId}/reviews/{reviewId}")
    public ResponseEntity<MessageResponseDTO> deleteComment(@PathVariable Long bookId, @PathVariable Long reviewId,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MessageResponseDTO response = reviewService.deleteReview(bookId, reviewId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
