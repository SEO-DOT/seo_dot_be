package com.example.seo_dot.book.service;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.book.dto.response.BookListResponseDTO;
import com.example.seo_dot.book.dto.response.KeywordResponseDTO;
import com.example.seo_dot.book.dto.response.NewBookListResponseDTO;
import com.example.seo_dot.book.repository.BookRepository;
import com.example.seo_dot.review.dto.response.BestReviewListResponseDTO;
import com.example.seo_dot.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.seo_dot.book.service.BookUtils.getBookIds;

@RequiredArgsConstructor
@Service
public class MainPageService {

    private final String[] NEW_BOOK_CATEGORY_CODE = {"0101", "0103", "0105", "0107", "0108"};
    private final String[] NEW_BOOK_CATEGORY_NAME = {"소설", "시/에세이", "인문", "가정/육아", "요리"};
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

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

    public List<BookListResponseDTO> getBestSeller() {

        List<BookListResponseDTO> result = bookRepository.findBestSellers();
        List<Long> bookIds = getBookIds(result);

        // keyword 조회 (In 사용)
        Map<Long, List<KeywordResponseDTO>> findKeywordsMap = bookRepository.findBestSellersKeywords(bookIds);
        result.forEach(o -> o.setKeywordList(findKeywordsMap.get(o.getBookId())));

        System.out.println(result.size());
        return result;
    }

    public List<BestReviewListResponseDTO> getBestReviews() {
        List<BestReviewListResponseDTO> result = reviewRepository.getBestReviews();
        return result;
    }
}
