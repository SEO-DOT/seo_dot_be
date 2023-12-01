package com.example.seo_dot.book.repository;

import com.example.seo_dot.book.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Query("SELECT k.keyword From Keyword k JOIN BookKeyword bk ON k.id = bk.keywordId WHERE bk.bookId = :bookId")
    List<String> findByKeyword(Long bookId);

}
