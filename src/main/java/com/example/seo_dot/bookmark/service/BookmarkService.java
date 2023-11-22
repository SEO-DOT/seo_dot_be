package com.example.seo_dot.bookmark.service;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.book.repository.BookRepository;
import com.example.seo_dot.bookmark.domain.Bookmark;
import com.example.seo_dot.bookmark.model.RequestBookmarkDto;
import com.example.seo_dot.bookmark.model.ResponseBookmarkDto;
import com.example.seo_dot.bookmark.model.ResponseBookmarkListDto;
import com.example.seo_dot.bookmark.model.ResponseDataDto;
import com.example.seo_dot.bookmark.repository.BookmarkRepository;
import com.example.seo_dot.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final BookRepository bookRepository;

    public ResponseDataDto createBookmark(RequestBookmarkDto requestBookmarkDto, UserDetailsImpl userDetailsImpl) {
        Long userId = userDetailsImpl.getUser().getId();
        Bookmark bookmark = Bookmark.builder()
                .category(requestBookmarkDto.getCategory())
                .color(requestBookmarkDto.getColor())
                .userId(userId)
                .build();

        bookmarkRepository.save(bookmark);
        return ResponseDataDto.ok();
    }

    public ResponseDataDto updateBookmark(Long id, RequestBookmarkDto requestBookmarkDto) {
        bookmarkRepository.findById(id).orElseThrow().updateBookmark(requestBookmarkDto);
        return ResponseDataDto.ok();
    }

    public ResponseDataDto deleteBookmark(Long id) {
        bookmarkRepository.deleteById(id);
        return ResponseDataDto.ok();
    }

    @Transactional(readOnly = true)
    public List<ResponseBookmarkDto> readBookmarkList(UserDetailsImpl userDetailsImpl) {
        return bookmarkRepository.findAllByUserId(userDetailsImpl.getUser().getId()).stream()
                .map(ResponseBookmarkDto::new)
                .collect(Collectors.toList());
    }

    public ResponseDataDto addBookmark(Long bookId, Long bookmarkId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        book.addBookmark(bookmarkId);
        bookmarkRepository.findById(bookmarkId).orElseThrow().createThumbnail(book);
        return ResponseDataDto.ok();
    }

    public ResponseDataDto cancelBookmark(Long bookId) {
        bookRepository.findById(bookId).orElseThrow().cancelBookmark();
        return ResponseDataDto.ok();
    }

    public List<ResponseBookmarkListDto> getBookmarkList(Long bookmarkId) {
        return bookRepository.findAllByBookmarkId(bookmarkId).stream()
                .map(ResponseBookmarkListDto::new)
                .collect(Collectors.toList());
    }
}