package com.example.seo_dot.bookmark.repository;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.bookmark.domain.Bookmark;
import com.example.seo_dot.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    boolean existsByUserIdAndBookId(Long userId, Long bookId);

    void deleteByUserIdAndBookId(Long userId, Long bookId);
}
