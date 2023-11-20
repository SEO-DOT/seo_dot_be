package com.example.seo_dot.bookmark.domain;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.bookmark.model.RequestBookmarkDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Builder
@AllArgsConstructor(access = PROTECTED)
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private String thumbnail;
    private String color;
    private Long userId;

    @Nullable
    private Long bookId;

    public void updateBookmark(RequestBookmarkDto requestBookmarkDto) {
        this.category = requestBookmarkDto.getCategory();
        this.color = requestBookmarkDto.getColor();
    }
}
