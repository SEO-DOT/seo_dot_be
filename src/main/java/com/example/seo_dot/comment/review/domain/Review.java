package com.example.seo_dot.comment.review.domain;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.comment.domain.Comment;
import com.example.seo_dot.comment.review.dto.request.ReviewModifyRequestDTO;
import com.example.seo_dot.global.entity.BaseEntity;
import com.example.seo_dot.comment.review.dto.request.ReviewCreateRequestDTO;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer score;

    private int likes;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "review")
    private List<Comment> commentList;

    private boolean deleted;

    private boolean spoiler;

    private boolean purchaseStatus;

    private Review(String contents, Book book, User user, Integer score, int likes, boolean spoiler, boolean purchaseStatus) {
        this.contents = contents;
        this.book = book;
        this.user = user;
        this.score = score;
        this.likes = likes;
        this.spoiler = spoiler;
        this.purchaseStatus = purchaseStatus;
    }

    public static Review createReview(Book book, User user, ReviewCreateRequestDTO reviewCreateRequestDTO, boolean purchaseStatus) {
        return new Review(reviewCreateRequestDTO.getContents(), book, user, reviewCreateRequestDTO.getScore(), 0, reviewCreateRequestDTO.isSpoiler(), purchaseStatus);
    }

    public void updateReview(ReviewModifyRequestDTO requestDTO) {
        this.contents = requestDTO.getContents();
    }

    //TODO 종속된 Comment의 개수만큼 Update Query 발생 예상
    public void deleteReview() {
        this.deleted = true;

        if (this.commentList != null && !this.commentList.isEmpty()) {
            this.commentList.forEach(Comment::deleteComment);
            this.commentList.clear();
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
