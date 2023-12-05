package com.example.seo_dot.book.domain;

import com.example.seo_dot.library.domain.Library;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "books")
@Entity
@AllArgsConstructor(access = PROTECTED)
@Builder
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libraryId")
    private Library library;

    public Integer getDiscountPrice() {
        if (this.price <= 0 || this.discountRate <= 0) {
            throw new IllegalArgumentException();
        }
        return this.price - (this.price * this.discountRate / 100);
    }

    public void updateViewCount() {
        this.viewCount++;
    }

    public void addBookmark(Library library) {
        this.library = library;
    }

    public void cancelBookmark() {
        this.library = null;
    }
}
