package com.example.seo_dot.bookmark.domain;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Builder
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long bookId;

    public Bookmark(User user, Book book) {
        this.userId = user.getId();
        this.bookId = book.getId();
    }

}
