package com.example.seo_dot.book.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "books")
@Entity
public class Book {

    @Id
    private Long id;
    private String title;
    private String author;
    private Integer price;
    private Integer discountRate;
    private String image;
    private String publisher;
    private String publicationDate;
    private String isbn;
    private String description;
    private Integer viewCount;
    private String status;
    private Integer stock;
    private Integer score;
    private String categoryCode;
    private String isAdultContent;
    private Long bookmarkId;

    public Integer getDiscountPrice() {
        if (this.price <= 0 || this.discountRate <= 0) {
            throw new IllegalArgumentException();
        }
        return this.price - (this.price * this.discountRate / 100);
    }

    public void updateViewCount() {
        this.viewCount++;
    }

    public void addBookmark(Long bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public void cancelBookmark() {
        this.bookmarkId = null;
    }
}
