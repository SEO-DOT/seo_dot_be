package com.example.seo_dot.mypage.dto.response;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.review.domain.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPageReviewResponseDTO {

    private Long reviewId;
    private String nickname;
    private String contents;
    private LocalDateTime createdDate;
    private int likes;
    private boolean liked;
    private Integer score;
    private int commentCount;
    private MyPageReviewListBook book;

    public MyPageReviewResponseDTO(Review review, MyPageReviewListBook book) {
        this.reviewId = review.getId();
        this.nickname = review.getUser().getNickname();
        this.contents = review.getContents();
        this.createdDate = review.getCreatedAt();
        this.likes = review.getLikes();
        this.score = review.getScore();
        this.commentCount = review.getCommentList().size();
        this.book = book;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Getter
    public static class MyPageReviewListBook {
        private String bookTitle;
        private String bookImage;
        private String author;
        private String publisher;

        public MyPageReviewListBook(Book book) {
            this.bookTitle = book.getTitle();
            this.bookImage = book.getImage();
            this.author = book.getAuthor();
            this.publisher = book.getPublisher();
        }
    }
}
