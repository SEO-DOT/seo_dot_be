package com.example.seo_dot.review.repository;

import com.example.seo_dot.review.domain.Review;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.seo_dot.review.domain.QReview.review;

@RequiredArgsConstructor
@Repository
public class QReviewRepositoryImpl implements QReviewRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Review> getReviewsOrderByCreatedAtDesc(Long bookId, Long userId, Pageable pageable) {
        List<Review> reviews = queryFactory
                .select(review)
                .from(review)
                .where(review.book.id.eq(bookId), review.deleted.isFalse())
                .orderBy(userReviewFirst(userId).asc(), review.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return createResultWithNextPage(pageable, reviews);
    }

    @Override
    public Slice<Review> getReviewsOrderByLikesDesc(Long bookId, Long userId, Pageable pageable) {
        List<Review> reviews = queryFactory
                .select(review)
                .from(review)
                .where(review.book.id.eq(bookId), review.deleted.isFalse())
                .orderBy(userReviewFirst(userId).asc(), review.likes.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return createResultWithNextPage(pageable, reviews);
    }

    private NumberExpression<Integer> userReviewFirst(Long userId) {
        NumberExpression<Integer> userReviewOrder = new CaseBuilder()
                .when(review.user.id.eq(userId)).then(0)
                .otherwise(1);
        return userReviewOrder;
    }

    private Slice<Review> createResultWithNextPage(Pageable pageable, List<Review> results) {

        boolean hasNext = false;

        if (results.size() > pageable.getPageSize()) {
            hasNext = true;
            results.remove(pageable.getPageSize());
        }
        return new SliceImpl<>(results, pageable, hasNext);
    }
}
