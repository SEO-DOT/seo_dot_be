package com.example.seo_dot.bookmark.repository;

import com.example.seo_dot.bookmark.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findAllByUserId(Long userId);
}
