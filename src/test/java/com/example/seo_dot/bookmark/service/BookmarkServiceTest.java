package com.example.seo_dot.bookmark.service;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.book.repository.BookRepository;
import com.example.seo_dot.bookmark.domain.Bookmark;
import com.example.seo_dot.bookmark.model.ResponseDataDto;
import com.example.seo_dot.bookmark.repository.BookmarkRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookmarkServiceTest {

    @InjectMocks
    private BookmarkService bookmarkService;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookmarkRepository bookmarkRepository;

    private Book book;
    private Bookmark bookmark;

    @BeforeEach
    public void init() {
        book = Book.builder()
                .id(1L)
                .image("image")
                .author("author")
                .categoryCode("categoryCode")
                .isbn("ISBN")
                .price(10000)
                .description("description")
                .title("title")
                .stock(200)
                .score(5)
                .publicationDate("publicationDate")
                .publisher("publisher")
                .viewCount(100)
                .discountRate(20)
                .status("status")
                .isAdultContent("true")
                .build();

        bookmark = Bookmark.builder()
                .id(1L)
                .color("color")
                .userId(1L)
                .category("category")
                .build();
    }

    @Test
    public void addBookmark() {
        when(bookRepository.findById(any()))
                .thenReturn(Optional.of(book));

        when(bookmarkRepository.findById(any()))
                .thenReturn(Optional.of(bookmark));

        ResponseDataDto responseDataDto = bookmarkService.addBookmark(1L, 1L);

        Assertions.assertThat(responseDataDto.getMessage()).isEqualTo("success");


    }

    @Test
    public void getBookmarkList() {

    }

    @Test
    public void readBookmarkList() {

    }
}