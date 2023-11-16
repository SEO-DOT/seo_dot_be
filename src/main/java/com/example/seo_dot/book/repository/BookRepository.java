package com.example.seo_dot.book.repository;

import com.example.seo_dot.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, QBookRepository {
    Optional<Book> findAllByBookmarkId(Long id);
}
