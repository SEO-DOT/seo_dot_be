package com.example.seo_dot.library.controller;

import com.example.seo_dot.library.model.RequestLibraryDto;
import com.example.seo_dot.library.service.LibraryService;
import com.example.seo_dot.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class LibraryController {

    private final LibraryService libraryService;

    @PostMapping
    public ResponseEntity createLibrary(@RequestBody RequestLibraryDto requestBookmarkDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ResponseEntity.ok().body(libraryService.createLibrary(requestBookmarkDto, userDetailsImpl));
    }

    @PutMapping("/{bookmarkId}")
    public ResponseEntity updateLibrary(@PathVariable Long bookmarkId, @RequestBody RequestLibraryDto requestBookmarkDto) {
        return ResponseEntity.ok().body(libraryService.updateLibrary(bookmarkId, requestBookmarkDto));
    }

    @DeleteMapping("/{bookmarkId}")
    public ResponseEntity deleteLibrary(@PathVariable Long bookmarkId) {
        return ResponseEntity.ok().body(libraryService.deleteLibrary(bookmarkId));
    }

    @GetMapping
    public ResponseEntity<List> getLibraries(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return ResponseEntity.ok().body(libraryService.readLibraryList(userDetailsImpl));
    }

    @GetMapping("/{bookmarkId}")
    public ResponseEntity getBookmarkList(@PathVariable Long bookmarkId) {
        return ResponseEntity.ok().body(libraryService.getBookmarkList(bookmarkId));
    }

    @PostMapping("/{bookmarkId}/{bookId}")
    public ResponseEntity addBookmark(@PathVariable Long bookId, @PathVariable Long bookmarkId) {

        return ResponseEntity.ok().body(libraryService.addBookmark(bookId, bookmarkId));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity cancelBookmark(@PathVariable Long bookId) {

        return ResponseEntity.ok().body(libraryService.cancelBookmark(bookId));
    }
}
