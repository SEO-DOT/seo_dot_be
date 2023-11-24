package com.example.seo_dot.book.dto.response;

import com.example.seo_dot.book.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class NewBookListResponseDTO {

    private String category;
    private List<List<MainBookListResponseDTO>> bookList = new ArrayList<>();

    @Getter
    @NoArgsConstructor
    public static class MainBookListResponseDTO {
        private Long bookId;
        private String title;
        private String author;
        private String publisher;
        private String image;
        private Integer discountRate;
        private Integer discountPrice;

        public MainBookListResponseDTO(Book book) {
            this.bookId = book.getId();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.publisher = book.getPublisher();
            this.image = book.getImage();
            this.discountRate = book.getDiscountRate();
            this.discountPrice = book.getDiscountPrice();
        }
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

