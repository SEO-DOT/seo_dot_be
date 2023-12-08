package com.example.seo_dot.comment.review.dto.response;

import com.example.seo_dot.comment.review.domain.Review;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReviewListResponseDTO {

    private Long id;
    private Long userId;
    private String nickname;
    private String contents;
    private int likes;
    private boolean liked;
    private Integer score;
    private int commentCount;
    private LocalDateTime createdDate;
    private List<CommentListResponseDTO> commentsList;
    private boolean spoiler;
    private boolean purchaseStatus;

    public ReviewListResponseDTO(Review review) {
        this.id = review.getId();
        this.userId = review.getUser().getId();
        this.nickname = review.getUser().getNickname();
        this.contents = review.getContents();
        this.likes = review.getLikes();
        this.score = review.getScore();
        this.commentCount = review.getCommentList().size();
        this.createdDate = review.getCreatedAt();
        this.commentsList = review.getCommentList().stream()
                .map(CommentListResponseDTO::new)
                .toList();
        this.spoiler = review.isSpoiler();
        this.purchaseStatus = review.isPurchaseStatus();
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
