package com.example.seo_dot.bookmark.model;

import com.example.seo_dot.book.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseBookmarkListDto {

    private Long bookId;
    private String image;
    private String title;

    public ResponseBookmarkListDto(Book book){
        this.bookId = book.getId();
        this.image = book.getImage();
        this.title = book.getTitle();
    }
}