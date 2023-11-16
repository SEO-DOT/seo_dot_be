package com.example.seo_dot.bookmark.controller;

import com.example.seo_dot.bookmark.model.RequestBookmarkDto;
import com.example.seo_dot.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookmarkController {

    public BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity createBookmark(@RequestBody RequestBookmarkDto requestBookmarkDto) {
        bookmarkService.createBookmark(requestBookmarkDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBookmark(@PathVariable Long id, @RequestBody RequestBookmarkDto requestBookmarkDto, @AuthenticationPrincipal UserDetails userDetails) {
        bookmarkService.updateBookmark(id, requestBookmarkDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBookmark(@PathVariable Long id) {
        bookmarkService.deleteBookmark(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List> getBookmarks(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok().body(bookmarkService.readBookmarkList(userDetails));
    }

    @GetMapping("/{id}")
    public ResponseEntity getBookmarkList(@PathVariable Long id) {
        return ResponseEntity.ok().body(bookmarkService.getBookmarkList(id));
    }

    @PostMapping("/{bookId}/{id}")
    public ResponseEntity addBookmark(@PathVariable Long bookId, @PathVariable Long bookmarkId) {
        bookmarkService.addBookmark(bookId, bookmarkId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity cancelBookmark(@PathVariable Long bookId) {
        bookmarkService.cancelBookmark(bookId);
        return ResponseEntity.ok().build();
    }
}
