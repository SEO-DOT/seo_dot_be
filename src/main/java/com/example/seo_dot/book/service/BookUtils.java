package com.example.seo_dot.book.service;

import com.example.seo_dot.book.dto.response.BookListResponseDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BookUtils {

    public static List<Long> getBookIds(Iterable<BookListResponseDTO> result) {
        List<Long> bookIds = StreamSupport.stream(result.spliterator(), false)
                .map(BookListResponseDTO::getBookId)
                .collect(Collectors.toList());
        return bookIds;
    }
}
