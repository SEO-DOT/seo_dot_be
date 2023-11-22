package com.example.seo_dot.comment.controller;

import com.example.seo_dot.comment.domain.CommentModifyRequestDTO;
import com.example.seo_dot.comment.dto.request.CommentCreateRequestDTO;
import com.example.seo_dot.comment.service.CommentService;
import com.example.seo_dot.global.dto.MessageResponseDTO;
import com.example.seo_dot.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/books/{bookId}/reviews/{reviewId}/comments")
    public ResponseEntity<MessageResponseDTO> createComment(@PathVariable Long bookId, @PathVariable Long reviewId,
                                                            @RequestBody CommentCreateRequestDTO requestDTO,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MessageResponseDTO response = commentService.createComment(bookId, reviewId, requestDTO, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/api/books/{bookId}/reviews/{reviewId}/comments/{commentId}")
    public ResponseEntity<MessageResponseDTO> modifyComment(@PathVariable Long bookId, @PathVariable Long reviewId,
                                                            @PathVariable Long commentId, @RequestBody CommentModifyRequestDTO requestDTO,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MessageResponseDTO response = commentService.modifyComment(bookId, reviewId, commentId, requestDTO, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/api/books/{bookId}/reviews/{reviewId}/comments/{commentId}")
    public ResponseEntity<MessageResponseDTO> deleteComment(@PathVariable Long bookId, @PathVariable Long reviewId,
                                                            @PathVariable Long commentId,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MessageResponseDTO response = commentService.deleteComment(bookId, reviewId, commentId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
