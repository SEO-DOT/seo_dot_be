package com.example.seo_dot.book.dto.response;

import com.example.seo_dot.book.domain.Book;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BookDetailResponseDTO {

    private Long bookId;
    private String image;
    private String title;
    private String status;
    private Integer stock;
    private String isbn;
    private Integer price;
    private Integer discountRate;
    private Integer discountPrice;
    private Integer score;
    private boolean bookmark;
    private String description;
    private String publicationDate;
    private String publisher;
    private String isAdultContent;
    private Integer viewCount;
    private List<String> keywordList = new ArrayList<>();
    private List<String> categoryList = new ArrayList<>();

    public BookDetailResponseDTO(Book book, List<String> keywordList, List<String> categoryList) {
        this.bookId = book.getId();
        this.image = book.getImage();
        this.title = book.getTitle();
        this.status = book.getStatus();
        this.stock = book.getStock();
        this.isbn = book.getIsbn();
        this.price = book.getStock();
        this.discountRate = book.getDiscountRate();
        this.discountPrice = book.getDiscountPrice();
        this.score = book.getScore();
        this.bookmark = bookmark;
        this.description = book.getDescription();
        this.publicationDate = book.getPublicationDate();
        this.publisher = book.getPublisher();
        this.isAdultContent = book.getIsAdultContent();
        this.viewCount = book.getViewCount();
        this.keywordList = keywordList;
        this.categoryList = categoryList;
    }
}
