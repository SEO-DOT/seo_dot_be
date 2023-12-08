package com.example.seo_dot.library.domain;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.library.model.RequestLibraryDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Builder
@AllArgsConstructor(access = PROTECTED)
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private String thumbnail;
    private String colorCode;
    private Long userId;

    public void updateLibrary(RequestLibraryDto requestBookmarkDto) {
        this.category = requestBookmarkDto.getCategory();
        this.colorCode = ColorCode.random();
    }

    public void createThumbnail(Book book) {
        this.thumbnail = book.getImage();
    }
}
