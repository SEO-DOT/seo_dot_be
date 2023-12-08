package com.example.seo_dot.bookmark.controller;

import com.example.seo_dot.bookmark.service.BookmarkService;
import com.example.seo_dot.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/{bookId}")
    public ResponseEntity addBookmark(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long bookId) {
        bookmarkService.addBookmark(userDetails.getUser(), bookId);
        return ResponseEntity.ok().build();
    }
}