package com.example.seo_dot.bookmark.service;

import com.example.seo_dot.book.repository.BookRepository;
import com.example.seo_dot.bookmark.domain.Bookmark;
import com.example.seo_dot.bookmark.model.RequestBookmarkDto;
import com.example.seo_dot.bookmark.model.ResponseBookmarkDto;
import com.example.seo_dot.bookmark.model.ResponseBookmarkListDto;
import com.example.seo_dot.bookmark.repository.BookmarkRepository;
import com.example.seo_dot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public void createBookmark(RequestBookmarkDto requestBookmarkDto) {
        Bookmark bookmark = Bookmark.builder()
                .category(requestBookmarkDto.getCategory())
                .color(requestBookmarkDto.getColor())
                .build();

        bookmarkRepository.save(bookmark);
    }

    public void updateBookmark(Long id, RequestBookmarkDto requestBookmarkDto) {
        bookmarkRepository.findById(id).orElseThrow().updateBookmark(requestBookmarkDto);
    }

    public void deleteBookmark(Long id) {
        bookmarkRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ResponseBookmarkDto> readBookmarkList(UserDetails userDetails) {
        Long userId = userRepository.findByUsername(userDetails.getUsername()).orElseThrow().getId();
        return bookmarkRepository.findAllByUserId(userId).stream()
                .map(ResponseBookmarkDto::new)
                .collect(Collectors.toList());
    }

    public void addBookmark(Long bookId, Long bookmarkId) {
        bookRepository.findById(bookId).orElseThrow().addBookmark(bookmarkId);
    }

    public void cancelBookmark(Long bookId) {
        bookRepository.findById(bookId).orElseThrow().cancelBookmark();
    }

    public List<ResponseBookmarkListDto> getBookmarkList(Long id) {
        return bookRepository.findAllByBookmarkId(id).stream()
                .map(ResponseBookmarkListDto::new)
                .collect(Collectors.toList());
    }
}