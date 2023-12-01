package com.example.seo_dot.review.controller;

import com.example.seo_dot.global.dto.MessageResponseDTO;
import com.example.seo_dot.review.service.ReviewLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReviewLikeController {

    private final ReviewLikeService reviewLikeService;


    @PostMapping("/api/books/{bookId}/reviews/{reviewId}/like")
    public ResponseEntity<MessageResponseDTO> reviewLike(@PathVariable Long bookId, @PathVariable Long reviewId, Long userId) {
        MessageResponseDTO response = reviewLikeService.reviewLike(bookId, reviewId, 1L);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
