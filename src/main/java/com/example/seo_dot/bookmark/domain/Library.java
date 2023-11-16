package com.example.seo_dot.bookmark.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Library {

    @Id
    private Long id;

    private String name;
    private String color;
    private Long userId;

    public Library(String name, String color, Long userId) {
        this.name = name;
        this.color = color;
        this.userId = userId;
    }
}
