package com.example.seo_dot.bookmark.controller;

import com.example.seo_dot.bookmark.model.RequestBookmarkDto;
import com.example.seo_dot.bookmark.model.ResponseDataDto;
import com.example.seo_dot.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity createBookmark(@RequestBody RequestBookmarkDto requestBookmarkDto, @AuthenticationPrincipal UserDetails userDetails) {
        bookmarkService.createBookmark(requestBookmarkDto, userDetails);
        return ResponseEntity.ok().body(ResponseDataDto.ok(OK));
    }

    @PutMapping("/{bookmarkId}")
    public ResponseEntity updateBookmark(@PathVariable Long bookmarkId, @RequestBody RequestBookmarkDto requestBookmarkDto) {
        bookmarkService.updateBookmark(bookmarkId, requestBookmarkDto);
        return ResponseEntity.ok().body(ResponseDataDto.ok(OK));
    }

    @DeleteMapping("/{bookmarkId}")
    public ResponseEntity deleteBookmark(@PathVariable Long bookmarkId) {
        bookmarkService.deleteBookmark(bookmarkId);
        return ResponseEntity.ok().body(ResponseDataDto.ok(OK));
    }

    @GetMapping
    public ResponseEntity<List> getBookmarks(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok().body(bookmarkService.readBookmarkList(userDetails));
    }

    @GetMapping("/{bookmarkId}")
    public ResponseEntity getBookmarkList(@PathVariable Long bookmarkId) {
        return ResponseEntity.ok().body(bookmarkService.getBookmarkList(bookmarkId));
    }

    @PostMapping("/{bookId}/{bookmarkId}")
    public ResponseEntity addBookmark(@PathVariable Long bookId, @PathVariable Long bookmarkId) {
        bookmarkService.addBookmark(bookId, bookmarkId);
        return ResponseEntity.ok().body(ResponseDataDto.ok(OK));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity cancelBookmark(@PathVariable Long bookId) {
        bookmarkService.cancelBookmark(bookId);
        return ResponseEntity.ok().body(ResponseDataDto.ok(OK));
    }
}
