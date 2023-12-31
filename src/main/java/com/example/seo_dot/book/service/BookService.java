package com.example.seo_dot.book.service;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.book.dto.request.PageParam;
import com.example.seo_dot.book.dto.response.BookDetailResponseDTO;
import com.example.seo_dot.book.dto.response.BookListResponseDTO;
import com.example.seo_dot.book.dto.response.BookRecommendResponseDTO;
import com.example.seo_dot.book.dto.response.KeywordResponseDTO;
import com.example.seo_dot.book.repository.BookRepository;
import com.example.seo_dot.book.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.example.seo_dot.book.service.BookUtils.getBookIds;

@RequiredArgsConstructor
@Service
public class BookService {

    private final String[] NEW_BOOK_CATEGORY_CODE = {"0101", "0103", "0105", "0107", "0108"};
    private final String[] NEW_BOOK_CATEGORY_NAME = {"소설", "시/에세이", "인문", "가정/육아", "요리"};
    private final BookRepository bookRepository;
    private final KeywordRepository keywordRepository;
//    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;


    @Transactional
    public BookDetailResponseDTO getBookDetail(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException());
        List<String> keywords = keywordRepository.findByKeyword(book.getId());
        Map<String, List<String>> categoryPathsMap = categoryService.getCategoryPathsMap();

//        List<String> categories = new ArrayList<>();
//        Category category = categoryRepository.findById(book.getCategoryCode()).orElseThrow();
//        categories.add(category.getCategoryName());

//        while (category != null && category.getParentCategoryCode() != null) {
//            category = category.getParentCategoryCode();
//            categories.add(category.getCategoryName());
//        }

//        Collections.reverse(categories);
        List<String> categories = categoryPathsMap.get(book.getCategoryCode());
        BookDetailResponseDTO response = new BookDetailResponseDTO(book, keywords, categories);
        book.updateViewCount();
        return response;
    }

    public Slice<BookListResponseDTO> getBookList(PageParam pageParam) {

        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getPer());
        // Book을 먼저 조회
        Slice<BookListResponseDTO> result = bookRepository.getBookList(pageParam ,pageable);
        List<Long> bookIds = getBookIds(result);

        // keyword 조회 (In 사용)
        Map<Long, List<KeywordResponseDTO>> findKeywordsMap = bookRepository.findBestSellersKeywords(bookIds);
        result.forEach(o -> o.setKeywordList(findKeywordsMap.get(o.getBookId())));

        return result;
    }

    public List<BookRecommendResponseDTO> getRecommendBookList(String category) {
        List<BookRecommendResponseDTO> result = bookRepository.getRecommendBookList(category);
        return result;
    }
}
