package com.example.seo_dot.book.repository;

import com.example.seo_dot.book.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
