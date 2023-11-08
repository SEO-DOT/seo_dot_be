package com.example.seo_dot.book.service;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.book.dto.request.PageParam;
import com.example.seo_dot.book.dto.response.*;
import com.example.seo_dot.book.repository.BookRepository;
import com.example.seo_dot.book.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public List<BookListResponseDTO> getBestSeller() {

        List<BookListResponseDTO> result = bookRepository.findBestSellers();
        List<Long> bookIds = getBookIds(result);

        // keyword 조회 (In 사용)
        Map<Long, List<KeywordResponseDTO>> findKeywordsMap = bookRepository.findBestSellersKeywords(bookIds);
        result.forEach(o -> o.setKeywordList(findKeywordsMap.get(o.getBookId())));

        System.out.println(result.size());
        return result;
    }

    public List<NewBookListResponseDTO> getNewBooks() {
        List<NewBookListResponseDTO> result = new ArrayList<>();
        for (int i = 0; i < NEW_BOOK_CATEGORY_CODE.length; i++) {
            result.add(new NewBookListResponseDTO());
            List<Book> newBookByCategoryCode = bookRepository.findNewBookByCategoryCode(NEW_BOOK_CATEGORY_CODE[i]);
            List<NewBookListResponseDTO.MainBookListResponseDTO> mainBookListResponseDTOList = new ArrayList<>();
            for (Book book : newBookByCategoryCode) {
                mainBookListResponseDTOList.add(new NewBookListResponseDTO.MainBookListResponseDTO(book));
            }
            result.get(i).setCategory(NEW_BOOK_CATEGORY_NAME[i]);
            result.get(i).getBookList().add(mainBookListResponseDTOList);
        }
        return result;
    }

    private List<Long> getBookIds(Iterable<BookListResponseDTO> result) {
        List<Long> bookIds = StreamSupport.stream(result.spliterator(), false)
                .map(BookListResponseDTO::getBookId)
                .collect(Collectors.toList());
        return bookIds;
    }

    public List<BookRecommendResponseDTO> getRecommendBookList(String category) {
        List<BookRecommendResponseDTO> result = bookRepository.getRecommendBookList(category);
        System.out.println(result.size());
        return result;
    }
}
