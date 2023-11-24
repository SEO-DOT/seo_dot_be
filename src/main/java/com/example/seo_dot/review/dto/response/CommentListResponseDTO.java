package com.example.seo_dot.review.dto.response;

import com.example.seo_dot.comment.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentListResponseDTO {

    private Long userId;
    private String nickName;
    private Long commentId;
    private Long reviewId;
    private String contents;
    private LocalDateTime createdDate;

    public CommentListResponseDTO(Comment comment) {
        this.userId = comment.getUser().getId();
        this.nickName = comment.getUser().getNickname();
        this.commentId = comment.getId();
        this.reviewId = comment.getReview().getId();
        this.contents = comment.getContents();
        this.createdDate = comment.getCreatedAt();
    }
}
