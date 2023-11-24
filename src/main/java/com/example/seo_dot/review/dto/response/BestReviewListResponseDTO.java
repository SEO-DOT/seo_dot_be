package com.example.seo_dot.review.dto.response;

import lombok.Getter;

@Getter
public class BestReviewListResponseDTO {

    private Long reviewId;
    private String nickname;
    private String contents;
    private Integer score;
    private boolean spoiler;
    private boolean purchaseStatus;
    private int likes;
    private int commentCount;
    private BestReviewBookResponseDTO book;

    public BestReviewListResponseDTO(Long reviewId, String nickname, String contents, Integer score, boolean spoiler, boolean purchaseStatus, int likes, int commentCount, BestReviewBookResponseDTO book) {
        this.reviewId = reviewId;
        this.nickname = nickname;
        this.contents = contents;
        this.score = score;
        this.spoiler = spoiler;
        this.purchaseStatus = purchaseStatus;
        this.likes = likes;
        this.commentCount = commentCount;
        this.book = book;
    }

    @Getter
    public static class BestReviewBookResponseDTO {

        private Long bookId;
        private String bookImage;
        private String bookTitle;
        private String bookAuthor;
        private String bookPublisher;

        public BestReviewBookResponseDTO(Long bookId, String bookImage, String bookTitle, String bookAuthor, String bookPublisher) {
            this.bookId = bookId;
            this.bookImage = bookImage;
            this.bookTitle = bookTitle;
            this.bookAuthor = bookAuthor;
            this.bookPublisher = bookPublisher;
        }
    }
}
