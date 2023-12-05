package com.example.seo_dot.bookmark.service;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.book.repository.BookRepository;
import com.example.seo_dot.bookmark.domain.Bookmark;
import com.example.seo_dot.bookmark.repository.BookmarkRepository;
import com.example.seo_dot.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {

    private final BookRepository bookRepository;
    private final BookmarkRepository bookmarkRepository;

    public void addBookmark(User user, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow();

        if (!bookmarkRepository.existsByUserIdAndBookId(user.getId(), book.getId())) {
            book.addBookmark();
            bookmarkRepository.save(new Bookmark(user, book));
        } else {
            book.deleteBookmark();
            bookmarkRepository.deleteByUserIdAndBookId(user.getId(), book.getId());
        }
    }
}
