package com.example.seo_dot.book.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "books_keywords")
@Entity
public class BookKeyword {

    @Id
    private Long id;

    private Long bookId;

    private Long keywordId;
}
