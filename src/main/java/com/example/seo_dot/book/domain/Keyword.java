package com.example.seo_dot.book.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "keywords")
@Entity
public class Keyword {

    @Id
    private Long id;

    private String keyword;

    public Keyword(Long id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }
}

