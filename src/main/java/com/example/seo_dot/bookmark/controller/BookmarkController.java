package com.example.seo_dot.bookmark.controller;

import com.example.seo_dot.bookmark.model.RequestBookmarkDto;
import com.example.seo_dot.bookmark.service.BookmarkService;
import com.example.seo_dot.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity createBookmark(@RequestBody RequestBookmarkDto requestBookmarkDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ResponseEntity.ok().body(bookmarkService.createBookmark(requestBookmarkDto, userDetailsImpl));
    }

    @PutMapping("/{bookmarkId}")
    public ResponseEntity updateBookmark(@PathVariable Long bookmarkId, @RequestBody RequestBookmarkDto requestBookmarkDto) {
        return ResponseEntity.ok().body(bookmarkService.updateBookmark(bookmarkId, requestBookmarkDto));
    }

    @DeleteMapping("/{bookmarkId}")
    public ResponseEntity deleteBookmark(@PathVariable Long bookmarkId) {
        return ResponseEntity.ok().body(bookmarkService.deleteBookmark(bookmarkId));
    }

    @GetMapping
    public ResponseEntity<List> getBookmarks(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return ResponseEntity.ok().body(bookmarkService.readBookmarkList(userDetailsImpl));
    }

    @GetMapping("/{bookmarkId}")
    public ResponseEntity getBookmarkList(@PathVariable Long bookmarkId) {
        return ResponseEntity.ok().body(bookmarkService.getBookmarkList(bookmarkId));
    }

    @PostMapping("/{bookmarkId}/{bookId}")
    public ResponseEntity addBookmark(@PathVariable Long bookId, @PathVariable Long bookmarkId) {

        return ResponseEntity.ok().body(bookmarkService.addBookmark(bookId, bookmarkId));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity cancelBookmark(@PathVariable Long bookId) {

        return ResponseEntity.ok().body(bookmarkService.cancelBookmark(bookId));
    }
}
