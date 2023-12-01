package com.example.seo_dot.book.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "categories")
@Entity
public class Category {

    @Id
    @Column(name = "category_code")
    private String categoryCode;

    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "parent_category_code")
    private Category parentCategoryCode;

    private int categoryLevel;
}
