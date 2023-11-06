package com.example.seo_dot.review.domain;

import com.example.seo_dot.comment.domain.Comment;
import com.example.seo_dot.global.entity.BaseEntity;
import com.example.seo_dot.review.dto.request.ReviewCreateRequestDTO;
import com.example.seo_dot.review.dto.request.ReviewModifyRequestDTO;
import com.example.seo_dot.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reviews")
@Entity
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contents;

    private Long bookId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer score;

    private int likes;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "review")
    private List<Comment> commentList;

    private boolean deleted;

    private Review(String contents, Long bookId, User user, Integer score, int likes) {
        this.contents = contents;
        this.bookId = bookId;
        this.user = user;
        this.score = score;
        this.likes = likes;
    }

    public static Review createReview(Long bookId, User user, ReviewCreateRequestDTO reviewCreateRequestDTO) {
        return new Review(reviewCreateRequestDTO.getContents(), bookId, user, reviewCreateRequestDTO.getScore(), 0);
    }

    public void updateReview(ReviewModifyRequestDTO requestDTO) {
        this.contents = requestDTO.getContents();
    }

    public void deleteReview() {
        this.deleted = true;

        if (commentList != null) {
            for (Comment comment : commentList) {
                comment.deleteComment();
            }
        }
    }

    public void increaseLikeCount() {
        this.likes++;
    }

    public void decreaseLikeCount() {
        if (this.likes <= 0) {
            throw new IllegalArgumentException();
        }
        this.likes--;
    }
}
