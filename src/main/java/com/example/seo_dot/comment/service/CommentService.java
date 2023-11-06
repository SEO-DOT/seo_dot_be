package com.example.seo_dot.comment.service;

import com.example.seo_dot.book.repository.BookRepository;
import com.example.seo_dot.comment.domain.CommentModifyRequestDTO;
import com.example.seo_dot.comment.dto.request.CommentCreateRequestDTO;
import com.example.seo_dot.comment.domain.Comment;
import com.example.seo_dot.comment.infrastructure.CommentRepository;
import com.example.seo_dot.global.dto.MessageResponseDTO;
import com.example.seo_dot.review.domain.Review;
import com.example.seo_dot.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public MessageResponseDTO createComment(Long bookId, Long reviewId, CommentCreateRequestDTO requestDTO, Long userId) {
        bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException());

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException());

        Comment comment = Comment.createComment(review, userId, requestDTO);

        commentRepository.save(comment);

        return MessageResponseDTO.createSuccessMessage201();
    }

    @Transactional
    public MessageResponseDTO modifyComment(Long bookId, Long reviewId, Long commentId, CommentModifyRequestDTO requestDTO, Long userId) {
        bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException());

        reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException());

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException());

        if (!comment.getUserId().equals(userId)) {
            throw new IllegalArgumentException();
        }

        comment.updateComment(requestDTO);

        return MessageResponseDTO.createSuccessMessage200();
    }

    @Transactional
    public MessageResponseDTO deleteComment(Long bookId, Long reviewId, Long commentId, Long userId) {
        bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException());

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException());

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException());

        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException();
        }

        comment.deleteComment();

        return MessageResponseDTO.createSuccessMessage200();
    }
}
