package com.example.seo_dot.comment.domain;

import com.example.seo_dot.comment.dto.request.CommentCreateRequestDTO;
import com.example.seo_dot.global.entity.BaseEntity;
import com.example.seo_dot.review.domain.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    private Long userId;

    private String contents;

    private boolean deleted;

    private Comment(Review review, Long userId, String contents) {
        this.review = review;
        this.userId = userId;
        this.contents = contents;
    }

    public static Comment createComment(Review review, Long userId, CommentCreateRequestDTO requestDTO) {
        return new Comment(review, userId, requestDTO.getContents());
    }

    public void updateComment(CommentModifyRequestDTO requestDTO) {
        this.contents = requestDTO.getContents();
    }

    public void deleteComment() {
        this.deleted = true;
    }
}
