package com.example.seo_dot.review.repository;

import com.example.seo_dot.review.domain.Review;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.seo_dot.review.domain.QReview.*;

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
                .orderBy(userReviewFirst(userId), review.createdAt.desc())
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
                .orderBy(userReviewFirst(userId), review.likes.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return createResultWithNextPage(pageable, reviews);
    }

    private OrderSpecifier<?> userReviewFirst(Long userId) {
        if (userId == null) {
            return new OrderSpecifier(Order.DESC, NullExpression.DEFAULT, OrderSpecifier.NullHandling.Default);
        }
        return review.user.id.eq(userId).desc();
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
