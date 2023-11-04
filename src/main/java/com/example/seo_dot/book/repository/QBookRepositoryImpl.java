package com.example.seo_dot.book.repository;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.book.dto.request.PageParam;
import com.example.seo_dot.book.dto.response.BookListResponseDTO;
import com.example.seo_dot.book.dto.response.KeywordResponseDTO;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.seo_dot.book.domain.QBook.book;
import static com.example.seo_dot.book.domain.QBookKeyword.bookKeyword;
import static com.example.seo_dot.book.domain.QKeyword.keyword1;

@Repository
@RequiredArgsConstructor
public class QBookRepositoryImpl implements QBookRepository {

    private final JPAQueryFactory queryFactory;

    public List<BookListResponseDTO> findBestSellers() {
        List<BookListResponseDTO> result = queryFactory
                .select(Projections.constructor(BookListResponseDTO.class, book.id,
                        book.title, book.author, book.publisher, book.image, book.description))
                .from(book)
                .orderBy(book.id.asc())
                .limit(10)
                .fetch();
        return result;
    }

    public Slice<BookListResponseDTO> getBookList(PageParam pageParam, Pageable pageable) {
        List<BookListResponseDTO> result = queryFactory
                .select(Projections.constructor(BookListResponseDTO.class, book.id,
                                book.title, book.author, book.publisher, book.image, book.description))
                .from(book)
                .where(book.categoryCode.startsWith(pageParam.getCategory()))
                .orderBy(book.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
        return createResultWithNextPage(pageable, result);
    }

    public List<Book> findNewBookByCategoryCode(String categoryCode) {
        List<Book> result = queryFactory
                .select(book)
                .from(book)
                .where(book.categoryCode.startsWith(categoryCode))
                .orderBy(book.publicationDate.desc())
                .limit(20)
                .fetch();
        return result;
    }

    public Map<Long, List<KeywordResponseDTO>> findBestSellersKeywords(List<Long> ids) {
        List<Tuple> keywordTuples = queryFactory
                .select(bookKeyword.bookId, Projections.constructor(KeywordResponseDTO.class, keyword1))
                .from(keyword1)
                .innerJoin(bookKeyword).on(bookKeyword.keywordId.eq(keyword1.id))
                .where(bookKeyword.bookId.in(ids))
                .fetch();

        Map<Long, List<KeywordResponseDTO>> result = new LinkedHashMap<>();
        for (Tuple tuple : keywordTuples) {
            Long bookId = tuple.get(bookKeyword.bookId);
            KeywordResponseDTO keyword = tuple.get(Projections.constructor(KeywordResponseDTO.class, keyword1));
            result.computeIfAbsent(bookId, k -> new ArrayList<>()).add(keyword);
        }
        return result;
    }

    private Slice<BookListResponseDTO> createResultWithNextPage(Pageable pageable, List<BookListResponseDTO> results) {

        boolean hasNext = false;

        if (results.size() > pageable.getPageSize()) {
            hasNext = true;
            results.remove(pageable.getPageSize());
        }
        return new SliceImpl<>(results, pageable, hasNext);
    }
}
