package com.example.seo_dot.book.dto.response;

import com.example.seo_dot.book.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class BookListResponseDTO {
    private Long bookId;
    private String title;
    private String author;
    private String publisher;
    private String image;
    private String description;
    private List<KeywordResponseDTO> keywordList = new ArrayList<>();

    public BookListResponseDTO(Long bookId, String title, String author, String publisher, String image, String description) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.image = image;
        this.description = description;
    }

    public void setKeywordList(List<KeywordResponseDTO> keywordList) {
        if (keywordList == null) {
            this.keywordList = new ArrayList<>();
            return;
        }
        this.keywordList = keywordList;
    }
}
