package com.example.seo_dot.book.controller;

import com.example.seo_dot.book.dto.response.BookListResponseDTO;
import com.example.seo_dot.book.dto.response.NewBookListResponseDTO;
import com.example.seo_dot.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MainPageController {

    private final BookService bookService;

    @GetMapping("/api/welcome/best")
    public ResponseEntity<List<BookListResponseDTO>> getBestSellers() {
        List<BookListResponseDTO> response = bookService.getBestSeller();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/api/welcome/new")
    public ResponseEntity<List<NewBookListResponseDTO>> getNewBooks() {
        List<NewBookListResponseDTO> response = bookService.getNewBooks();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
