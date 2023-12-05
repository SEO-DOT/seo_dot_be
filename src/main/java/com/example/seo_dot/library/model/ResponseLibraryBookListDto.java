package com.example.seo_dot.library.model;

import com.example.seo_dot.book.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseLibraryBookListDto {

    private Long bookId;
    private String image;
    private String title;

    public ResponseLibraryBookListDto(Book book){
        this.bookId = book.getId();
        this.image = book.getImage();
        this.title = book.getTitle();
    }
}