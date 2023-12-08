package com.example.seo_dot.library.repository;

import com.example.seo_dot.library.domain.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Long> {

    List<Library> findAllByUserId(Long userId);
}
