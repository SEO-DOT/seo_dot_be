package com.example.seo_dot.book.dto.response;

import com.example.seo_dot.book.domain.Book;
import lombok.Getter;

@Getter
public class BookRecommendResponseDTO {
    private Long bookId;
    private String image;
    private String title;
    private String author;
    private Integer discountPrice;

    public BookRecommendResponseDTO(Book book) {
        this.bookId = book.getId();
        this.image = book.getImage();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.discountPrice = book.getDiscountPrice();
    }
}
