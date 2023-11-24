package com.example.seo_dot.book.repository;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.book.dto.request.PageParam;
import com.example.seo_dot.book.dto.response.BookListResponseDTO;
import com.example.seo_dot.book.dto.response.BookRecommendResponseDTO;
import com.example.seo_dot.book.dto.response.KeywordResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Map;

public interface QBookRepository {
    List<Book> findNewBookByCategoryCode(String categoryCode);

    List<BookListResponseDTO> findBestSellers();

    Slice<BookListResponseDTO> getBookList(PageParam pageParam, Pageable pageable);

    Map<Long, List<KeywordResponseDTO>> findBestSellersKeywords(List<Long> ids);

    List<BookRecommendResponseDTO> getRecommendBookList(String category);
}
