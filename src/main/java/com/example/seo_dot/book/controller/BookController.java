package com.example.seo_dot.book.controller;

import com.example.seo_dot.book.dto.request.PageParam;
import com.example.seo_dot.book.dto.response.BookDetailResponseDTO;
import com.example.seo_dot.book.dto.response.BookListResponseDTO;
import com.example.seo_dot.book.dto.response.BookRecommendResponseDTO;
import com.example.seo_dot.book.dto.response.PageDto;
import com.example.seo_dot.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/books/{bookId}")
    public ResponseEntity<BookDetailResponseDTO> getBookDetail(@PathVariable Long bookId) {
        BookDetailResponseDTO response = bookService.getBookDetail(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/api/category")
    public ResponseEntity<PageDto<List<BookListResponseDTO>>> getCategoryList(@ModelAttribute PageParam pageParam) {
        Slice<BookListResponseDTO> result = bookService.getBookList(pageParam);
        PageDto<List<BookListResponseDTO>> response = new PageDto<>(result.getContent(), result.hasNext());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/api/books/recommend")
    public ResponseEntity<List<BookRecommendResponseDTO>> getRecommendBookList(@RequestParam String category) {
        List<BookRecommendResponseDTO> response = bookService.getRecommendBookList(category);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
